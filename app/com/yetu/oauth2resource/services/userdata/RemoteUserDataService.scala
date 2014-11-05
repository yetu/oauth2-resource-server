package com.yetu.oauth2resource
package services
package userdata

import com.yetu.oauth2resource.model.{User, Email}
import com.yetu.oauth2resource.settings.OAuth2ProviderSettings
import play.api.{libs, Logger}
import play.api.Play.current
import play.api.libs.json
import play.api.libs.json.{ JsError, JsSuccess }
import play.api.libs.ws.{ WS, WSResponse }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Promise, Future}
import scala.util.{Success, Failure, Try}

class RemoteUserDataService(oAuth2ProviderSettings: OAuth2ProviderSettings) extends UserDataService {

  def getUserProfile(access_token: String): Future[User] = {
    val userInfo = WS.url(oAuth2ProviderSettings.UserInfoPath)
      .withQueryString(oAuth2ProviderSettings.ACCESS_TOKEN -> access_token)
      .get()

    userInfo map tryConvertUserInfo
  }

  private def tryConvertUserInfo(response: WSResponse): User = {
    response.json.validate[User] match {
      case u: JsSuccess[User] => u.get
      case e: JsError         => throw UserInfoException(s"Wrong response for user info. Cause: $e")
    }
  }

}
