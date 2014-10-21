package controllers


import com.yetu.oauth2resource.actions.Authorized
import play.api.mvc._

import scala.concurrent.Future

class Application(auth: Authorized) extends Controller {

  def index = auth.async {
    Future.successful(Ok("Great! The access token you provided was correct."))
  }

}