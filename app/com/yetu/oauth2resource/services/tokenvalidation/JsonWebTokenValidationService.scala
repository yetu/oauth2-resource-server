package com.yetu.oauth2resource.services.tokenvalidation

import com.plasmaconduit.jwt.JSONWebToken
import com.yetu.oauth2resource.model.{Token, ValidationResponse}
import com.yetu.oauth2resource.settings.OAuth2ProviderSettings
import com.yetu.oauth2resource.utils.JsonUtility
import play.api.libs.json.{JsError, JsSuccess}
import play.api.mvc.Result
import play.api.mvc.Results._

import scala.concurrent.Future
import scala.util.{Failure, Success}

class JsonWebTokenValidationService(oAuth2ProviderSettings: OAuth2ProviderSettings) extends TokenValidationService {

  override protected def validateToken(accessToken: Token): Future[Either[Result, ValidationResponse]] = {

    JSONWebToken.verify(oAuth2ProviderSettings.jsonWebTokenSignaturePublicKey,
      token = accessToken,
      audience = oAuth2ProviderSettings.audience,
      issuer = oAuth2ProviderSettings.issuer) match {
      case Success(value) => {
        val response = JsonUtility.convertPlasmaJsonToPlayJson(value).validate[ValidationResponse]
        response match {
          case JsSuccess(v, _) => Future.successful(Right(v))
          case e: JsError => Future.successful(Left(BadRequest("access token is not a properly formatted json web token: " + JsError.toFlatJson(e).toString())))
        }
      }
      case Failure(exception) => Future.successful(Left(Unauthorized(s"${exception.getMessage}")))
    }

  }
}
