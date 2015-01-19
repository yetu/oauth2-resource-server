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
      case Failure(ex) => Future.failed(TokenParseException(ex.getMessage))
    }
  }

  def verifyToken(jWSObject: JWSObject, key: RSAPublicKey): Either[Exception, ValidationResponse] = {
    val verifier = new RSASSAVerifier(key)
    if (!jWSObject.verify(verifier)) {
      Left(ValidationTokenException("Access token not verified"))
    } else {
      val json = Json.parse(jWSObject.getPayload.toString)
      val res = json.as[ValidationResponse]

      def ifEmpty: Either[Exception, ValidationResponse] = Left(ValidationTokenException("Token is corrupted on expiration data provided"))
      res.exp.fold(ifEmpty)(time =>
        if (time <= System.currentTimeMillis) Right(res) else Left(new TokenExpiredException("Expired access token")))
    }
  }

  private def getUserId(result: WSResponse): Either[Exception, ValidationResponse] = {
    if (result.status == OK) {
      val validationResult = result.json.validate[ValidationResponse]
      validationResult.fold(
        errors => Left(ValidationTokenException(s"Wrong json response from token validation endpoint: $errors")),
        valRes => Right(valRes)
      )
    }
    else {
      Left(new Exception("Unexpected responce status " + result.status))
    }
  }
}
