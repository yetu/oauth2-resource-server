package com.yetu.oauth2resource.services.tokenvalidation

import com.yetu.oauth2resource.model._
import play.api.mvc.Result
import play.api.mvc.Results._

import scala.concurrent.Future


trait TokenValidationService {

  protected def validateToken(accessToken: Token): Future[Either[Result, ValidationResponse]]

  def validate(accessToken: Option[Token]): Future[Either[Result, ValidationResponse]] = {
    accessToken match {
      case Some(value) => validateToken(value)
      case _           => Future.successful(Left(BadRequest("No access_token provided")))
    }
  }
}
