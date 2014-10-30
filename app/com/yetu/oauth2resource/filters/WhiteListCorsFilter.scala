package com.yetu.oauth2resource.filters

import play.api.mvc.{RequestHeader, _}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait WhiteListCorsFilter extends CorsFilterHelper {

  def whiteListCORSUrls: List[String]

  override def apply(next: (RequestHeader) => Future[Result])(requestHeader: RequestHeader): Future[Result] = {

    next(requestHeader).map { result =>

      if (whiteListCORSUrls.contains(requestHeader.uri)) {
        result.withHeaders(corsHeaders: _*)
      } else {
        result
      }
    }
  }
}




