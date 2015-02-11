package com.yetu.oauth2resource.services.tokenvalidation


case class ValidationTokenException(message: String, cause: Throwable = null) extends Exception

case class TokenParseException(message: String, cause: Throwable = null) extends Exception

case class TokenExpiredException(message: String, cause: Throwable = null) extends Exception

case class InvalidAudienceException(message: String, cause: Throwable = null) extends Exception
