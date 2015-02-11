package com.yetu.oauth2resource.services.tokenvalidation

import com.yetu.oauth2resource.base.BaseSpec
import com.yetu.oauth2resource.registry.{ValidationServiceRegistry, ValidationTestRegistry}
import com.yetu.oauth2resource.services.tokenvalidation.InvalidAudienceException
import com.yetu.oauth2resource.utils.RoutesHelper
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.MustMatchers._
import org.scalatest.Matchers._


class OAuth2TokenValidationServiceTest extends BaseSpec with Matchers with RoutesHelper with ScalaFutures  {

  implicit val defaultPatience =
    PatienceConfig(timeout =  Span(2, Seconds), interval = Span(5, Millis))

  val mockTokenValidationService = ValidationTestRegistry.tokenValidationService
  val realValidationService = ValidationServiceRegistry.tokenValidationService

  "Validation service" must "reject dummy token after validation remote server" in {
    whenReady(mockTokenValidationService.validate(Some(wrongToken))) { either =>
      either must be('left)
    }
  }

  "Validation service" must "accept correct token after validation remote server" in {
    whenReady(mockTokenValidationService.validate(Some(correctToken))) { either =>
      either must be('right)
    }
  }

  "Validation service" must "validate access token (jwt) locally with public key" in {
    whenReady(realValidationService.validate(Some(correctToken), JWTTokenMethod)) { either =>
      either must be('right)
    }
  }

  "Validation service" must "reject wrong token (not jwt)" in {
    whenReady(realValidationService.validate(Some(wrongToken), JWTTokenMethod)) { either =>
      either must be('left)
    }
  }

  "Validation service" must "validate access token (jwt) and make sure that JWT is valid at least for next month" in {
    whenReady(realValidationService.validate(Some(correctToken), JWTTokenMethod)) { either =>
      either must be('right)
      either.right.get.exp.get must be > System.currentTimeMillis() / 1000 + (60 * 24 * 30)
    }
  }

  "Validation service" must "reject valid access token but with wrong audience" in {
    whenReady(realValidationService.validate(Some(correctTokenInvalidAud), JWTTokenMethod)) { either =>
      either must be('left)
      either.left.get mustBe a[InvalidAudienceException]
    }


  }
}
