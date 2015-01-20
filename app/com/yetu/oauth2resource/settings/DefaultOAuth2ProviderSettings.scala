package com.yetu.oauth2resource.settings

import com.typesafe.config.ConfigFactory

object DefaultOAuth2ProviderSettings extends OAuth2ProviderSettings {
  //TODO Seems to be overkill
  val config = ConfigFactory.load()
  val Oauth2providerBaseUrl = config.getString("oauth2provider.baseUrl")
}
