package com.yetu.oauth2resource.model

import play.api.libs.json._


/**
 * JSON received when validating an access_token at /oauth2/validate or when decoding the access_token (which is a JSON web token).
 *
 * For all the three-letter fields, see https://tools.ietf.org/html/draft-ietf-oauth-json-web-token-32#section-4.1
 * for a description.
 */
case class ValidationResponse(userUUID: Option[String] = None, //TODO: get rid of this, use sub instead
                              scope: Option[String] = None,
                              userId: Option[String] = None, //TODO: get rid of this, use sub instead
                              userEmail: Option[String] = None, //TODO: get rid of this, use sub instead
                              sub: Option[String] = None,
                              aud: Option[String] = None,
                              iss: Option[String] = None,
                              iat: Option[Long] = None,
                              exp: Option[Long] = None,
                              nbf: Option[Long] = None,
                              jti: Option[String] = None
                               )

object ValidationResponse {
  implicit val validationFormat = Json.format[ValidationResponse]
}


