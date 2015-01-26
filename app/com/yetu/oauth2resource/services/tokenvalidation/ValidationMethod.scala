package com.yetu.oauth2resource.services.tokenvalidation

//method of access token validation
sealed trait ValidationMethod

case object JWTTokenMethod extends ValidationMethod

case object RemoteMethod extends ValidationMethod