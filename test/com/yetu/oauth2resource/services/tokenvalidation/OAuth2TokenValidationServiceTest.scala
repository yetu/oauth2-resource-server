package com.yetu.oauth2resource.services.tokenvalidation

import com.yetu.oauth2resource.base.BaseSpec
import com.yetu.oauth2resource.model.ValidationResponse
import com.yetu.oauth2resource.registry.ValidationTestRegistry
import com.yetu.oauth2resource.utils.RoutesHelper
import org.scalatest._
import org.scalatest.concurrent.{ScalaFutures, Futures}
import org.scalatestplus.play.PlaySpec
import play.api.mvc.Result

import scala.collection.mutable._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class OAuth2TokenValidationServiceTest extends BaseSpec with Matchers with RoutesHelper with ScalaFutures {


  val tokenValidationService = ValidationTestRegistry.tokenValidationService

  "ValidationService" should "reject dummy token after validation remote server" in {
    whenReady(tokenValidationService.validate(Some(wrongToken))) { either =>
      either should be ('left)
    }
  }

  "ValidationService" should "accept correct token after validation remote server" in {
    whenReady(tokenValidationService.validate(Some(correctToken))) { either =>
      either should be ('right)
    }
  }


}
