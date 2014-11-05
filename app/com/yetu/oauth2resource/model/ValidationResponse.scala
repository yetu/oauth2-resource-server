package com.yetu.oauth2resource.model

import play.api.libs.json._

case class ValidationResponse(userUUID: Option[String] = None,
                              scope: Option[String] = None,
                              userId: Option[String] = None,
                              userEmail: Option[String] = None,
                              deprecationWarning: Option[String] = None )


object ValidationResponse {
  implicit val validationFormat = Json.format[ValidationResponse]
}


