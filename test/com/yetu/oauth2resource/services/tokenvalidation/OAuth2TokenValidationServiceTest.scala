package com.yetu.oauth2resource.services.tokenvalidation

import com.yetu.oauth2resource.base.BaseSpec
import com.yetu.oauth2resource.registry.ValidationTestRegistry
import com.yetu.oauth2resource.utils.RoutesHelper
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

class OAuth2TokenValidationServiceTest extends BaseSpec with Matchers with RoutesHelper with ScalaFutures {


  val tokenValidationService = ValidationTestRegistry.tokenValidationService

  "Validation service" should "reject dummy token after validation remote server" in {
    whenReady(tokenValidationService.validate(Some(wrongToken))) { either =>
      either should be('left)
    }
  }

  "Validation service" should "accept correct token after validation remote server" in {
    whenReady(tokenValidationService.validate(Some(correctToken))) { either =>
      either should be('right)
    }
  }

  "Validation service" should "validate access token (jwt) locally with public key" in {
    whenReady(tokenValidationService.validate(Some(correctToken), JWTTokenMethod())) { either =>
      either should be('left)
    }
  }


}
