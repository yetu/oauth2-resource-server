package com.yetu.oauth2resource.services.tokenvalidation

import com.yetu.oauth2resource.model._
import play.api.mvc.Result
import play.api.mvc.Results._

import scala.concurrent.Future

//method of access token validation
abstract class ValidationMethod

case class JWTToken() extends ValidationMethod

case class RemoteMethod() extends ValidationMethod


trait TokenValidationService {

  protected def validateTokenRemote(accessToken: Token): Future[Either[Result, ValidationResponse]]
  protected def validateTokenWithJWT(accessToken: Token): Future[Either[Result, ValidationResponse]]

  def validate(accessToken: Option[Token], validationMethod: ValidationMethod =  RemoteMethod()): Future[Either[Result, ValidationResponse]] = {
    accessToken match {
      case Some(value) => validationMethod match {
        case RemoteMethod() => validateTokenRemote(value)
        case JWTToken() => validateTokenWithJWT(value)
        case _ => Future.successful(Left(BadRequest("Wrong validation method")))
      }
      case _ => Future.successful(Left(BadRequest("No access_token provided")))
    }
  }
}
