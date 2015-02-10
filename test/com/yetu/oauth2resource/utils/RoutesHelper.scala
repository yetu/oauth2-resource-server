package com.yetu.oauth2resource.utils

import com.typesafe.config.ConfigFactory
import com.yetu.oauth2resource.model.ValidationResponse

/**
 * mix in to your Play routes tests
 */
trait RoutesHelper {
  implicit def importedUrlBuilderConversion = AuthUrlBuilder.str2urlBuilder _

  val config = ConfigFactory.load()
  val correctToken = config.getString("oauth.test.token")
  val wrongToken = "wrong_access_token"

  val successfulValidationResponse = ValidationResponse(userUUID = Some("87656789-lkhghj-98678"),
    clientId = Some("com.yetu.someapp.id"),
    userEmail = Some("test@test.com"),
    scope = Some("basic"),
    exp = Some(1733500020),
    aud = Some("events"),
    iss = Some("https://auth.yetudev.com"),
    sub= Some("ubject.with.dots"))
}
