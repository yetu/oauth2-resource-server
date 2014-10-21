package com.yetu.oauth2resource.services.tokenvalidation

case class TokenValidationException(message: String, cause: Throwable = null) extends RuntimeException(message, cause)
