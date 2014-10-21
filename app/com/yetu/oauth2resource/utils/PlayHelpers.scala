package com.yetu.oauth2resource.utils

import play.api.libs.json.{ Json, Writes }
import play.api.mvc.Result
import play.api.mvc.Results.{ BadRequest, Ok }

import scala.util.control.NonFatal

object PlayHelpers {

  /**
   * Wraps any non-fatal exception in BadRequest play status
   */
  val GenericError = new PartialFunction[Throwable, Result] {
    def apply(t: Throwable) = BadRequest(t.getMessage)
    override def isDefinedAt(x: Throwable) = NonFatal(x)
  }

  /**
   * Converts any value to response with OK status and json body
   */
  def asJson[T](value: T)(implicit w: Writes[T]) = Ok(Json.toJson[T](value)(w))
}
