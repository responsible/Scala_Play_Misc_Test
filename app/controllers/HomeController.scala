package controllers

import javax.inject._
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    //    Redirect("/count")
    Ok(views.html.index("Your new application is ready."))
  }

  def print(message: String) = Action {
    //    Redirect("/count")
    Ok(views.html.index(message))
  }

  def redirect = Action {
    Redirect(controllers.routes.HomeController.index())
  }

  def flashTest = Action {
    Ok(views.html.index("Flash Test")).flashing("message" -> "success flash")
  }

}
