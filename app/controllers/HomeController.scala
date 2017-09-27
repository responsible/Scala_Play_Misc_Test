package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def print(message: String) = Action {
    Ok(views.html.index(message))
  }

  def redirect = Action {
    Redirect(controllers.routes.HomeController.index())
  }

  def flashTest = Action {
    Ok(views.html.index("Flash Test")).flashing("message" -> "success flash")
  }

}
