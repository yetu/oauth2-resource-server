package com.yetu.oauth2resource.services.tokenvalidation

import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec

import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.yetu.oauth2resource.model.ValidationResponse
import com.yetu.oauth2resource.settings.OAuth2ProviderSettings
import play.api.Play.current
import play.api.http.Status.OK
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.libs.json.Json
import play.api.libs.ws.{WS, WSResponse, WSResponseHeaders}
import play.api.Logger


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}


class OAuth2TokenValidationService(oAuth2ProviderSettings: OAuth2ProviderSettings) extends TokenValidationService {

  // needs server restart on new key issue
  lazy val publicKey: RSAPublicKey = Await.result(fetchPublicKey(oAuth2ProviderSettings.PublicKeyPath), 10 seconds)

  def validateTokenRemote(accessToken: String): Future[Either[Exception, ValidationResponse]] = {
    val validationResult = WS.url(oAuth2ProviderSettings.TokenValidationPath)
      .withQueryString(oAuth2ProviderSettings.ACCESS_TOKEN -> accessToken)
      .get()

    validationResult.map(getUserId)
  }

  def validateTokenWithJWT(accessToken: String): Future[Either[Exception, ValidationResponse]] = {
      processJWT(accessToken, publicKey)
  }

  def fetchPublicKey(url: String): Future[RSAPublicKey] = {
    WS.url(url)
      .getStream()
      .flatMap(f => generatePublicKeyFromBytes(f))
      .map(f => {
          Logger.info("Fetched and cached public key")
          f
      })
  }

  def generatePublicKeyFromBytes(f: (WSResponseHeaders, Enumerator[Array[Byte]])): Future[RSAPublicKey] = {
    val (headers, responce) = f

    val consume = Iteratee.consume[Array[Byte]]()
    responce(consume)
      .flatMap(i => i.run)
      .map(key => {
      val kf = KeyFactory.getInstance("RSA")
      kf.generatePublic(new X509EncodedKeySpec(key)).asInstanceOf[RSAPublicKey]
    })
  }

  def processJWT(accessToken: String, key: RSAPublicKey): Future[Either[Exception, ValidationResponse]] = {
    Try(JWSObject.parse(accessToken))
      .map(jwtObject => verifyToken(jwtObject, key)) match {
      case Success(e) => Future.successful(e)
      case Failure(ex) =>
        Logger.error("Token parse exception, failed to parse token", ex)
        Future.successful(Left(TokenParseException(ex.getMessage, ex)))
    }
  }

  def verifyToken(jWSObject: JWSObject, key: RSAPublicKey): Either[Exception, ValidationResponse] = {
    val verifier = new RSASSAVerifier(key)
    if (!jWSObject.verify(verifier)) {
      val ex: ValidationTokenException = ValidationTokenException("Access token not verified")
      Logger.error("Token parse exception, failed to parse token", ex)
      Left(ex)
    } else {
      val json = Json.parse(jWSObject.getPayload.toString)
      val res = json.as[ValidationResponse]

      def ifEmpty: Either[Exception, ValidationResponse] = {
        val ex: ValidationTokenException = ValidationTokenException("Token is corrupted on expiration data provided")
        Logger.error("Validation exception, expired data is not provided", ex)
        Left(ex)
      }

      res.exp.fold(ifEmpty)(time =>
        if (time >= System.currentTimeMillis / 1000) {
          Logger.debug(s"Token validated with key and time, exp date is $res")
          Right(res)
        } else {
          Logger.error(s"Token validation failed due to expiration ex time is $res")
          Left(new TokenExpiredException(s"Expired access token with $res"))
        })
    }
  }

  private def getUserId(result: WSResponse): Either[Exception, ValidationResponse] = {
    if (result.status == OK) {
      val validationResult = result.json.validate[ValidationResponse]
      validationResult.fold(
        errors => {
          val ex: ValidationTokenException = ValidationTokenException(s"Wrong json response from token validation endpoint: $errors")
          Logger.error(s"Wrong json response from token validation endpoint $result",ex)
          Left(ex)},
        valRes => Right(valRes)
      )
    }
    else {
      Left(new Exception("Unexpected responce status " + result.status))
    }
  }
}
