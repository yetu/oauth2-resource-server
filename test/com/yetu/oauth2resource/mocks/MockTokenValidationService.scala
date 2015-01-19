package com.yetu.oauth2resource.mocks

import com.yetu.oauth2resource.model.{Token, ValidationResponse}
import com.yetu.oauth2resource.services.tokenvalidation.{ValidationTokenException, OAuth2TokenValidationService, TokenValidationService}
import com.yetu.oauth2resource.settings.OAuth2ProviderSettings
import com.yetu.oauth2resource.utils.RoutesHelper

import scala.concurrent.Future

class MockTokenValidationService(oAuth2ProviderSettings: OAuth2ProviderSettings) extends TokenValidationService with RoutesHelper {

  override def validateTokenRemote(accessToken: Token): Future[Either[Exception, ValidationResponse]] = {
    if (accessToken == correctToken) {
      Future.successful(Right(successfulValidationResponse))
    } else {
      Future.successful(Left(ValidationTokenException("Error of json token validation")))
    }
  }

  override protected def validateTokenWithJWT(accessToken: Token): Future[Either[Exception, ValidationResponse]] = {
    val realService = new OAuth2TokenValidationService(oAuth2ProviderSettings)
    realService.validateTokenWithJWT(accessToken)
  }
}