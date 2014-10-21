package com.yetu.oauth2resource.base

import com.yetu.oauth2resource.model.ValidationResponse

/**
 * mix in to your Play routes tests
 */
trait RoutesHelper {
  implicit def importedUrlBuilderConversion = com.yetu.oauth2resource.utils.AuthUrlBuilder.str2urlBuilder _

  val correctToken = "correct_access_token"
  val wrongToken = "wrong_access_token"

  val successfulValidationResponse = ValidationResponse(userUUID = "87656789-lkhghj-98678", userEmail = Some("test@test.com"), scope = Some("basic"))

}

