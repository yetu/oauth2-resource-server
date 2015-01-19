package com.yetu.oauth2resource.services.tokenvalidation


case class ValidationTokenException(message: String) extends Exception

case class TokenParseException(message: String) extends Exception

case class TokenExpiredException(message: String) extends Exception
