package model

import play.api.libs.json.{JsObject, Json}

object JsonResponse {
  def apply(data: JsObject = Json.obj(), code: Int = 200, msg: String = ""): JsObject =
    Json.obj(
      "data" -> data,
      "code" -> code,
      "msg" -> msg
    )
}
