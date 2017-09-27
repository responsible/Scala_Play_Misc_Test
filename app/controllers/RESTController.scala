package controllers

import javax.inject.{Inject, Singleton}

import model.JsonResponse
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class RESTController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def login = Action { request =>
    Ok(JsonResponse())
  }

  def publicResource = Action.async {
    Future {
      Ok(JsonResponse(data = Json.obj(
        "id" -> 1,
        "name" -> "test"
      )))
    }
  }
}
