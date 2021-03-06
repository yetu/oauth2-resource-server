package com.yetu.oauth2resource.actions

import com.yetu.oauth2resource.model.User
import com.yetu.oauth2resource.services.tokenvalidation.{TokenParseException, TokenValidationService, ValidationTokenException}
import com.yetu.oauth2resource.services.userdata.UserDataService
import com.yetu.oauth2resource.settings.OAuth2ProviderSettings
import com.yetu.oauth2resource.utils.AuthorizedRequest
import play.api.Logger
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 *         This is a refinement action used to cover the endpoints with token validation
 */
class Authorized(
                  tokenValidation: TokenValidationService,
                  userData: UserDataService,
                  oAuth2ProviderSettings: OAuth2ProviderSettings)
  extends ActionBuilder[AuthorizedRequest]
  with ActionRefiner[Request, AuthorizedRequest] with Results {

  type UserValidationResult = Future[Either[Result, User]]

  override protected def refine[A](request: Request[A]): Future[Either[Result, AuthorizedRequest[A]]] = {
    val access_token = request getQueryString oAuth2ProviderSettings.ACCESS_TOKEN

    // can we do it better?
    val validatedToken = tokenValidation validate access_token
    validatedToken map {
      case Right(valResponse) => Right(new AuthorizedRequest[A](valResponse, request))
      // delegate the initial wrong value to the future
      case Left(ex) => Left(ex match {
          case ValidationTokenException(msg, _) => Unauthorized(msg)
          case TokenParseException(msg, _) => Unauthorized(s"Can't parse token - $msg")
          case e =>
            Logger.error("Unexpected exception caught", e)
            InternalServerError("Unexpected error happened" + e.getMessage)
        })
    } recover {
      case e =>
        Logger.error("Unexpected exception caught", e)
        Left(InternalServerError(s"Unexpected error happened "+ e.getMessage))
    }
  }
}
