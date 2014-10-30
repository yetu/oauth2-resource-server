package com.yetu.oauth2resource.registry

import com.yetu.oauth2resource.mocks.MockTokenValidationService
import com.yetu.oauth2resource.services.tokenvalidation.TokenValidationService


trait ValidationTestRegistry extends ValidationServiceRegistry {

  import com.softwaremill.macwire.MacwireMacros._

  lazy override val tokenValidationService: TokenValidationService = wire[MockTokenValidationService]


}
