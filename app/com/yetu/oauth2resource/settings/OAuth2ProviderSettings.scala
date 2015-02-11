package com.yetu.oauth2resource.settings

import java.util

import scala.collection.JavaConversions._


trait OAuth2ProviderSettings {

  /**
   * This base url is the url to your authentication provider,
   * for example "https://auth.yetu.me"
   * and must be defined when you extend this trait
   */
  def Oauth2providerBaseUrl : String

  /**
   * Url to the endpoint which validates an OAuth2 access_token
   * This endpoint expects an access_token to be sent in the query parameters
   */
  def TokenValidationPath: String = Oauth2providerBaseUrl + Defaults.tokenValidationRelativePath

  /**
   *  Url to the endpoint which gives back a userProfile
   *  This endpoint expects an access_token to be sent in the query parameters
   */
  def UserInfoPath: String = Oauth2providerBaseUrl + Defaults.userProfileRelativePath

  /**
   *  Fetch publci cert file
   */
  def PublicKeyPath: String = Oauth2providerBaseUrl + Defaults.publicCertUrlRelativePath

  /**
   * The query string key holding an access token.
   */
  def ACCESS_TOKEN : String = "access_token"


  /**
   * These default paths are valid on yetu's OAuth2Provider
   */
  object Defaults {
    import com.typesafe.config.ConfigFactory
    val config = ConfigFactory.load()
    val publicCertUrlRelativePath = config.getString("oauth2provider.relativePaths.publicKey")
    val tokenValidationRelativePath =  config.getString("oauth2provider.relativePaths.tokenValidation")
    val userProfileRelativePath = config.getString("oauth2provider.relativePaths.userProfile")
    val targetAudience: List[String] = config.getStringList("oauth2provider.targetAudiences").toList
  }
}
