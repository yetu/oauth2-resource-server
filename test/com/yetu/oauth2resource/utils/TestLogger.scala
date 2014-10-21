package com.yetu.oauth2resource.utils

import play.api.Logger

trait TestLogger {
  private val testlogger = Logger("TEST")

  def log(s: String) = testlogger.warn(s)
}
