package registry

import com.yetu.oauth2resource.registry.ValidationServiceRegistry
import com.yetu.oauth2resource.settings.OAuth2ProviderSettings

import controllers.Application

object MyRegistry extends ValidationServiceRegistry {

  override lazy val oAuth2ProviderSettings: OAuth2ProviderSettings = MyOAuth2ProviderSettings

  lazy val applicationController = new Application(authorizedAction)

    // alternatively:
//  import com.softwaremill.macwire.MacwireMacros._
//  lazy val applicationController = wire[Application]

}