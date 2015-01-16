package com.yetu.oauth2resource.mocks

import com.yetu.oauth2resource.model.{Token, ValidationResponse}
import com.yetu.oauth2resource.services.tokenvalidation.TokenValidationService
import play.api.mvc.Result
import play.api.mvc.Results._


import com.yetu.oauth2resource.utils.RoutesHelper

import scala.concurrent.Future

class MockTokenValidationService extends TokenValidationService with RoutesHelper {

  override def validateTokenRemote(accessToken: Token): Future[Either[Result, ValidationResponse]] = {
    if (accessToken == correctToken) {
      Future.successful(Right(successfulValidationResponse))
    } else {
      Future.successful(Left(Unauthorized("")))
    }
  }

  override protected def validateTokenWithJWT(accessToken: Token): Future[Either[Result, ValidationResponse]] = {
    throw new NotImplementedError("Not implemeted")
  }
}