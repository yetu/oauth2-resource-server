package com.yetu.oauth2resource.services.userdata

/**
 * Thrown when there is some problem with user info fetched from auth server
 */
case class UserInfoException(msg: String) extends RuntimeException(msg)
