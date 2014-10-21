package com.yetu.oauth2resource.utils

import com.yetu.oauth2resource.model.ValidationResponse
import play.api.mvc.{Request, WrappedRequest}

/**
 *         This is a request wrapper which will have the user model aggregated
 *         along with the default request after the token is authorized
 *
 *         The validationResponse is the info we get from the oauth2/validate endpoint
 */
class AuthorizedRequest[A](val validationResponse: ValidationResponse, request: Request[A]) extends WrappedRequest[A](request)
