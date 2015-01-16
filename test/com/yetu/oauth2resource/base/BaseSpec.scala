package com.yetu.oauth2resource.base


import com.yetu.oauth2resource.utils.TestLogger
import org.scalatest.{FlatSpec, FlatSpecLike, FunSpecLike, FunSpec}
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.test.FakeApplication

class BaseSpec extends FlatSpec with OneAppPerSuite with TestLogger {

  implicit override lazy val app: FakeApplication =
    FakeApplication()
}
