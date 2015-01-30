package com.yetu.oauth2resource.mocks

import com.yetu.oauth2resource.model.{Token, ValidationResponse}
import com.yetu.oauth2resource.services.tokenvalidation._
import com.yetu.oauth2resource.settings.OAuth2ProviderSettings
import com.yetu.oauth2resource.utils.RoutesHelper
import play.api.Logger

import scala.concurrent.Future

class MockTokenValidationService(oAuth2ProviderSettings: OAuth2ProviderSettings) extends TokenValidationService with RoutesHelper {

  protected def validateTokenRemote(accessToken: Token) = mockValidation(accessToken)

  protected def validateTokenWithJWT(accessToken: Token) = mockValidation(accessToken)

  def mockValidation(accessToken: Token) ={
    if (accessToken == correctToken) {
      Logger.debug(s"returned mock successfulValidationResponse $successfulValidationResponse")
      Future.successful(Right(successfulValidationResponse))
    } else {
      Future.successful(Left(ValidationTokenException("Error of json token validation")))
    }
  }
}