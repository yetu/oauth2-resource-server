# OAuth2 Resource Server library

Contains re-usable code for access token validation with the yetu oauth2provider server.


**Currently (October 2014) the process of registering an app with yetu is not yet supported for people outside the yetu organization - but we want to change this soon. Also, we intend on open-sourcing our oauth2-provider implementation in the upcoming months, then you would be able to use both projects for your own use. Until that is done, this library will be only of limited use outside of yetu.**

The main feature is the Authorized playframework action that validates with a configurable oauth2 provider (currently only yetu's oauth2provider is supported) whether a given access token is valid:

```
# conf/routes
GET     /                           @controllers.Application.index
```

```scala
import com.yetu.oauth2resource.actions.Authorized

class Application(auth: Authorized) extends Controller {

  def index = auth.async {
    Future.successful(Ok("Great! The access token you provided was correct."))
  }
}
```

call with:

```
http://localhost:9000/?access_token=...
```

###Warning!!!

Since we use JWT in the project that validates tokens against public key from auth.yetudev.com we cache public key in memory.
If you change it, please restart server with app using this library.

## Versions and dependencies

This library is cross-compiled against Scala 2.10.4 and 2.11.2

It uses the Play 2.3.5 version plugin, and macwire for dependency injection.

## How to use this library

[ ![Download](https://api.bintray.com/packages/yetu/maven/oauth2-resource-server/images/download.svg) ](https://bintray.com/yetu/maven/oauth2-resource-server/_latestVersion)

The best way is to have a look at the sample project, located in the `sampleOAuth2ResourceServer` sub folder.

Specifically have a look at these configuration files:

* [build.sbt](sampleOAuth2ResourceServer/build.sbt)
* [project/plugins.sbt](sampleOAuth2ResourceServer/project/plugins.sbt)
* [project/build.properties](sampleOAuth2ResourceServer/project/build.properties)
* [conf/routes](sampleOAuth2ResourceServer/conf/routes)

As well as into the code structure of the `sampleOAuth2ResourceServer/app/controllers.Application` and the `sampleOAuth2ResourceServer/app/registry.MyRegistry` classes. 

###More about JWT

[Used library](http://connect2id.com/products/nimbus-jose-jwt#example)
[JWT general info](http://jwt.io/)

## License

We use the MIT license. See [license](LICENSE.md) for details.

## How to develop locally / contribute to this library

See [contributing](CONTRIBUTING.md) for details on running the project, contributing, and releasing new versions.
