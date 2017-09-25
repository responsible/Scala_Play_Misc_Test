package model

import play.api.mvc.{AbstractController, Action, BaseController, InjectedController}
import play.api.mvc.Results._

import scala.concurrent.Future

object Auth {
  def BasicAuth[A](action: Action[A]): Action[A] = Action.async(action.parser) { request =>
    val submittedCredentials: Option[List[String]] = for {
      authHeader <- request.headers.get("Authorization")
      parts <- authHeader.split(' ').drop(1).headOption
    } yield new String(org.apache.commons.codec.binary.Base64.decodeBase64(parts.getBytes)).split(':').toList

    submittedCredentials.collect {
      case u :: p :: Nil if check(u, p) =>
    }.map(_ => action(request)).getOrElse {
      Future.successful(Unauthorized(
        JsonResponse(
          msg = "Wrong username or password",
          code = 401)
      ).withHeaders("WWW-Authenticate" -> """Basic realm="Secured Area""""))
    }
  }

  def check(username: String, password: String): Boolean = {
    if (username == "admin" && password == "123456") true
    else false
  }
}
