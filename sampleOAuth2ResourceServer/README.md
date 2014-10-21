# Sample OAuth2 Resource server template

Sample OAuth2 Resource server using the `"com.yetu" %% "oauth2-resource-server" % "0.1.2"` library.

## Usage:

Make sure you have sbt installed.

from the root directory of this project, execute

```
sbt run
```

This should run your application on `localhost:9000`

Open a browser and make requests to 

```
# should give a 400 no access token provided:
http://localhost:9000/


# should give a 401 Unauthorized since the access token is invalid
http://localhost:9000/?access_token=aaa
```

Get a valid access token from auth.yetudev.com or auth.yetu.me (you must have a registered yetu app with registered OAuth2 credentials (clientId, clientSecret, redirectURI, ...). Then you can use that access token in the above request. 

Currently (October 2014) this process of registering an app with yetu is not yet supported for people outside the yetu organization - but we want to change this soon. Also, we intend on open-sourcing our oauth2-provider implementation in the upcoming months, then you would be able to use both projects for your own use. 

