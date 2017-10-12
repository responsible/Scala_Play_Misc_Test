package filters

import javax.inject.Inject

import akka.stream.Materializer
import model.JsonResponse
import org.apache.commons.codec.binary.Base64
import play.api.mvc.Results.Unauthorized
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class AuthFilter @Inject()(implicit override val mat: Materializer, exec: ExecutionContext) extends Filter {
  override def apply(next: (RequestHeader) => Future[Result])(request: RequestHeader) = {
    getUserAndPassFromHttpAuthHeader(request) match {
      case Some(username :: password :: Nil) if check(username, password) => next(request)
      case _ => Future.successful(Unauthorized(
        JsonResponse(
          msg = "Wrong username or password",
          code = 401)
      ).withHeaders("WWW-Authenticate" -> """Basic realm="Secured Area""""))
    }
  }

  private def getUserAndPassFromHttpAuthHeader(requestHeader: RequestHeader): Option[List[String]] = {
    for {
      authHeader <- requestHeader.headers.get("Authorization")
      parts <- authHeader.split(' ').drop(1).headOption
    } yield new String(Base64.decodeBase64(parts.getBytes)).split(':').toList
  }

  def check(username: String, password: String): Boolean =
    username == "admin" && password == "123456"
}
