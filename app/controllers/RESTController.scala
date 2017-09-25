package controllers

import javax.inject.{Inject, Singleton}

import model.{Auth, JsonResponse}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class RESTController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def login = Action { request =>
    Ok(JsonResponse())
  }

  def publicResource = Action { request =>
    Ok(JsonResponse(data = Json.obj(
      "id" -> 1,
      "name" -> "test"
    )))
  }
}
