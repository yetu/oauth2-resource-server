package com.yetu.oauth2resource.services.tokenvalidation

import com.yetu.oauth2resource.model._

import scala.concurrent.Future

//method of access token validation
abstract class ValidationMethod

case class JWTTokenMethod() extends ValidationMethod

case class RemoteMethod() extends ValidationMethod


trait TokenValidationService {

  protected def validateTokenRemote(accessToken: Token): Future[Either[Exception, ValidationResponse]]
  protected def validateTokenWithJWT(accessToken: Token): Future[Either[Exception, ValidationResponse]]

  def validate(accessToken: Option[Token], validationMethod: ValidationMethod =  RemoteMethod()): Future[Either[Exception, ValidationResponse]] = {
    accessToken match {
      case Some(value) => validationMethod match {
        case RemoteMethod() => validateTokenRemote(value)
        case JWTTokenMethod() => validateTokenWithJWT(value)
        case _ => Future.successful(Left(new Exception("Wrong validation method")))
      }
      case _ => Future.successful(Left(new Exception("No access_token provided")))
    }
  }
}
