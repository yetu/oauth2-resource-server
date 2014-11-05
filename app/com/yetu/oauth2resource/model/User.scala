package com.yetu.oauth2resource.model

import play.api.libs.json.Json

/**
 * custom user case class used for outputting JSON dynamically depending on the scope that is authorized.
 *
 * All fields should be of type Option[XZY] and have a default value of None.
 */
case class User(userId: Option[String] = None,
                firstName: Option[String] = None,
                lastName: Option[String] = None,
                email: Option[String] = None,
                registrationDate: Option[String] = None,
                householdIds: Option[List[String]] = None,
                contactInfo: Option[ContactInfo] = None,
                imageUrl: Option[String] = None
                 )


object User {
  implicit val userFormat = Json.format[User]
}




