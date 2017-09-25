package filters

import javax.inject.Inject

import akka.stream.Materializer
import model.JsonResponse
import play.api.mvc.Results.Unauthorized
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class AuthFilter @Inject()(implicit override val mat: Materializer,
                           exec: ExecutionContext) extends Filter {
  override def apply(f: (RequestHeader) => Future[Result])(request: RequestHeader) = {
    val submittedCredentials: Option[List[String]] = for {
      authHeader <- request.headers.get("Authorization")
      parts <- authHeader.split(' ').drop(1).headOption
    } yield new String(org.apache.commons.codec.binary.Base64.decodeBase64(parts.getBytes)).split(':').toList

    if (submittedCredentials.collect {
      case username :: password :: Nil if check(username, password) =>
    }.nonEmpty)
      f(request)
    else
      Future.successful(Unauthorized(
        JsonResponse(
          msg = "Wrong username or password",
          code = 401)
      ).withHeaders("WWW-Authenticate" -> """Basic realm="Secured Area""""))
  }

  def check(username: String, password: String): Boolean = {
    if (username == "admin" && password == "123456") true
    else false
  }
}
