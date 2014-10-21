package com.yetu.oauth2resource.model

import play.api.libs.json.Json


case class User(
  userId: String,
  firstName: String,
  lastName: String,
  email: String,
  imageUrl: Option[String] = None)

object User {
  implicit val userModelFormat = Json.format[User]
}