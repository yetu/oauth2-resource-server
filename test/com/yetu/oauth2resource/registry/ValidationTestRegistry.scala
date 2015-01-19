package com.yetu.oauth2resource.registry

import com.yetu.oauth2resource.services.tokenvalidation.{OAuth2TokenValidationService, TokenValidationService}
import com.yetu.oauth2resource.settings.{DefaultOAuth2ProviderSettings, OAuth2ProviderSettings}


object ValidationTestRegistry extends ValidationServiceRegistry {

  import com.softwaremill.macwire.MacwireMacros._

  lazy override val tokenValidationService: TokenValidationService = wire[OAuth2TokenValidationService]

  //override oAuth2ProviderSettings when including this library.
  override def oAuth2ProviderSettings: OAuth2ProviderSettings = DefaultOAuth2ProviderSettings
}
