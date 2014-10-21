package com.yetu.oauth2resource.mocks

import com.yetu.oauth2resource.base.RoutesHelper
import com.yetu.oauth2resource.model.{ValidationResponse, Token}
import com.yetu.oauth2resource.services.tokenvalidation.TokenValidationService
import play.api.mvc.Result
import play.api.mvc.Results._

import scala.concurrent.Future

class MockTokenValidationService extends TokenValidationService with RoutesHelper {

  override protected def validateToken(accessToken: Token): Future[Either[Result, ValidationResponse]] = {
    if (accessToken == correctToken) {
      Future.successful(Right(successfulValidationResponse))
    } else {
      Future.successful(Left(Unauthorized("")))
    }
  }
}
