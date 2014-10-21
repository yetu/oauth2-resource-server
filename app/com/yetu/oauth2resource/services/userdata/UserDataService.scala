package com.yetu.oauth2resource.services.userdata

import com.yetu.oauth2resource.model.{User, Email}

import scala.concurrent.Future
import scala.util.Try

trait UserDataService {

  def getUserProfile(access_token: String): Future[Try[User]]
}
