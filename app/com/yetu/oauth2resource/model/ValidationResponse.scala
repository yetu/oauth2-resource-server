package com.yetu.oauth2resource.model

import play.api.libs.json._

/**
 * This case class is exactly copy of the class that is in the oauth2Provider (com.yetu.oauth2provider.models.ValidationResponse)
 * Any changes in oauth2provider should be reflected here as well
 *
 * TODO: The OAuth2Provider should include this library and share this ValidationResponse object.
 *
 */
case class ValidationResponse(userUUID: String,
                              scope: Option[String] = None,
                              userId: Option[String] = None,
                              userEmail: Option[String] = None,
                              deprecationWarning: Option[String] = None )


object ValidationResponse {
  implicit val validationReads = Json.reads[ValidationResponse]
}


