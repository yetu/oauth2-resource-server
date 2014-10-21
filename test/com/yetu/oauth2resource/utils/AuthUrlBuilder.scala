package com.yetu.oauth2resource.utils

import play.api.libs.json.{JsNull, JsValue}
import play.api.mvc.Result
import play.api.test.Helpers._
import play.api.test.{FakeHeaders, FakeRequest}

import scala.Some
import scala.concurrent.Future

class AuthUrlBuilder(val urlBase: String) extends TestLogger {
  def get = request(urlBase, GET)
  def post = request(urlBase, POST)

  def ?(token: AuthUrlBuilder): AuthUrlBuilder = new AuthUrlBuilder(urlBase + "?access_token=" + token.urlBase)
  def ?(token: String): AuthUrlBuilder = ?(new AuthUrlBuilder(token))
  def &(p: (String, String)): AuthUrlBuilder = new AuthUrlBuilder(urlBase + '&' + p._1 + '=' + p._2)
  def /(path: AuthUrlBuilder): AuthUrlBuilder = new AuthUrlBuilder(urlBase + '/' + path.urlBase)

  private def request(url: String, method: String, headers: FakeHeaders = FakeHeaders(), parameters: JsValue = JsNull): Future[Result] = {
    val optResponse = route(FakeRequest(method, url, headers, parameters))

    optResponse match {
      case Some(response) =>
        log(s"status $url: ${status(response)}")
        log(s"content $url: ${contentAsString(response)}")

        val redirectLocationUrl = redirectLocation(response)
        redirectLocationUrl.map(x =>
          log(s"redirectLocation $url: ${redirectLocation(response)}"))

        response
      case None => throw new Exception("The wrong url requested: " + url)
    }

  }
}

object AuthUrlBuilder {
  implicit def str2urlBuilder(s: String) = new AuthUrlBuilder(s)
}
