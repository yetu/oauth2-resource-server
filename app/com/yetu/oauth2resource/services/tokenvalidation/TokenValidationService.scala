package com.yetu.oauth2resource.services.tokenvalidation

import com.yetu.oauth2resource.model._
import play.api.Logger

import scala.concurrent.Future

trait TokenValidationService {

  protected def validateTokenRemote(accessToken: Token): Future[Either[Exception, ValidationResponse]]

  protected def validateTokenWithJWT(accessToken: Token): Future[Either[Exception, ValidationResponse]]

  def validate(accessToken: Option[Token], validationMethod: ValidationMethod = RemoteMethod()): Future[Either[Exception, ValidationResponse]] = {
    accessToken match {
      case Some(value) => validationMethod match {
        case RemoteMethod() =>
          Logger.info(s"Validating token $accessToken with JWT")
          validateTokenRemote(value)
        case JWTTokenMethod() =>
          Logger.info(s"Validating token $accessToken with JWT")
          validateTokenWithJWT(value)
        case _ =>
          Logger.error("Wrong validation method provided")
          Future.successful(Left(new ValidationTokenException("Wrong validation method")))
      }
      case _ =>
        Logger.error("No access token provided")
        Future.successful(Left(new ValidationTokenException("No access_token provided")))
    }
  }
}
