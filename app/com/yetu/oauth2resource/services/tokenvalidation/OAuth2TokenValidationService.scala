package com.yetu.oauth2resource.services.tokenvalidation

import com.yetu.oauth2resource.model.{ValidationResponse}
import com.yetu.oauth2resource.settings.OAuth2ProviderSettings
import play.api.Play.current
import play.api.libs.ws.{WS, WSResponse}
import play.api.mvc.Result
import play.api.mvc.Results._
import play.api.http.Status.OK

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class OAuth2TokenValidationService(oAuth2ProviderSettings: OAuth2ProviderSettings) extends TokenValidationService {

  def validateTokenRemote(accessToken: String): Future[Either[Result, ValidationResponse]] = {
    val validationResult = WS.url(oAuth2ProviderSettings.TokenValidationPath)
      .withQueryString(oAuth2ProviderSettings.ACCESS_TOKEN -> accessToken)
      .get()

    validationResult.map(getUserId)
  }

  def validateTokenWithJWT(accessToken : String) = {
     Future.failed(new Throwable);
  }

  private def getUserId(result: WSResponse): Either[Result, ValidationResponse] = {
    if (result.status == OK) {
      val validationResult = result.json.validate[ValidationResponse]
      validationResult.fold(
        errors => Left(InternalServerError(s"Wrong json response from token validation endpoint: $errors")),
        valRes => Right(valRes)
      )
    }
    else {
      Left(new Status(result.status))
    }
  }
}
