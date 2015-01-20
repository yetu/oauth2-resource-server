package com.yetu.oauth2resource.services.tokenvalidation

//method of access token validation
abstract class ValidationMethod

case class JWTTokenMethod() extends ValidationMethod

case class RemoteMethod() extends ValidationMethod