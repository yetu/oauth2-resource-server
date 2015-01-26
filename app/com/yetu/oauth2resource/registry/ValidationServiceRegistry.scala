package com.yetu.oauth2resource.registry

import com.softwaremill.macwire.MacwireMacros._
import com.yetu.oauth2resource.actions.Authorized
import com.yetu.oauth2resource.services.tokenvalidation.{OAuth2TokenValidationService, TokenValidationService}
import com.yetu.oauth2resource.services.userdata.{RemoteUserDataService, UserDataService}
import com.yetu.oauth2resource.settings.{OAuth2ProviderSettings, DefaultOAuth2ProviderSettings}

/**
 * This class should hold all the services that will be executed in application
 */
trait ValidationServiceRegistry {


  lazy val tokenValidationService: TokenValidationService = wire[OAuth2TokenValidationService]

  lazy val userDataService: UserDataService = wire[RemoteUserDataService]

  lazy val authorizedAction: Authorized = wire[Authorized]



  //override oAuth2ProviderSettings when including this library.
  def oAuth2ProviderSettings: OAuth2ProviderSettings

}

object ValidationServiceRegistry extends ValidationServiceRegistry {
  override def oAuth2ProviderSettings: OAuth2ProviderSettings = DefaultOAuth2ProviderSettings
}