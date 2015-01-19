package com.yetu.oauth2resource.services.tokenvalidation

import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.security.{KeyFactory, PublicKey}

import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.typesafe.config.ConfigFactory
import com.yetu.oauth2resource.model.ValidationResponse
import com.yetu.oauth2resource.settings.OAuth2ProviderSettings
import play.api.Play.current
import play.api.http.Status.OK
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.libs.json.Json
import play.api.libs.ws.ning.NingWSResponse
import play.api.libs.ws.{WS, WSResponse}
import play.api.mvc.Results._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}


class OAuth2TokenValidationService(oAuth2ProviderSettings: OAuth2ProviderSettings) extends TokenValidationService {

  val config = ConfigFactory.load()

  def validateTokenRemote(accessToken: String): Future[Either[Exception, ValidationResponse]] = {
    val validationResult = WS.url(oAuth2ProviderSettings.TokenValidationPath)
      .withQueryString(oAuth2ProviderSettings.ACCESS_TOKEN -> accessToken)
      .get()

    validationResult.map(getUserId)
  }

  def validateTokenWithJWT(accessToken: String): Future[Either[Exception, ValidationResponse]] = {
    val res: Future[PublicKey] = fetchPublicKey(config.getString("oauth2provider.publicKey"))

    res.flatMap(key => {
      processJWT(accessToken, key.asInstanceOf[RSAPublicKey]) match {
        case Success(e) => Future.successful(e)
        case Failure(ex) => Future.failed(ex)
      }
    })
  }

  def processJWT(accessToken: String, key: RSAPublicKey) = {
    Try(JWSObject.parse(accessToken))
      .map(jwtObject => verifyToken(jwtObject, key))
  }

  def verifyToken(jWSObject: JWSObject, key: RSAPublicKey) = {
    val verifier = new RSASSAVerifier(key)
    if (jWSObject.verify(verifier)) {
      Left(ValidationException("Access token not verified"))
    } else {
      val json = Json.parse(jWSObject.getPayload.toString)
      val res = json.as[ValidationResponse]
      Right(res)
    }
  }

  def fetchPublicKey(url: String): Future[PublicKey] = {
    WS.url(url)
      .get()
      .map(responseToBytes)
      .flatMap(generatePublicKeyFromBytes)
  }

  def responseToBytes(responce: WSResponse): Enumerator[Array[Byte]] = {
    val asStream = responce.underlying[NingWSResponse].ahcResponse.getResponseBodyAsStream
    Status(OK).chunked(Enumerator.fromStream(asStream)).body
  }


  def generatePublicKeyFromBytes(data: Enumerator[Array[Byte]]): Future[PublicKey] = {
    val consume = Iteratee.consume[Array[Byte]]()
    data(consume)
      .flatMap(i => i.run)
      .map(a => {
      KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(a))
    })
  }


  private def getUserId(result: WSResponse): Either[Exception, ValidationResponse] = {
    if (result.status == OK) {
      val validationResult = result.json.validate[ValidationResponse]
      validationResult.fold(
        errors => Left(ValidationException(s"Wrong json response from token validation endpoint: $errors")),
        valRes => Right(valRes)
      )
    }
    else {
      Left(new Exception("Unexpected responce status " + result.status))
    }
  }
}
