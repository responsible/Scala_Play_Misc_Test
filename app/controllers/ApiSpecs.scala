package controllers

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits._
//import com.iheart.playSwagger.SwaggerSpecGenerator
import play.api.cache.Cached
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

class ApiSpecs @Inject() (cached: Cached) extends Controller {
  implicit val cl = getClass.getClassLoader

  // The root package of your domain classes, play-swagger will automatically generate definitions when it encounters class references in this package.
  // In our case it would be "com.iheart", play-swagger supports multiple domain package names
  val domainPackage = "YOUR.DOMAIN.PACKAGE"
  val secondDomainPackage = "YOUR.OtherDOMAIN.PACKAGE"
//  private lazy val generator = SwaggerSpecGenerator(domainPackage, secondDomainPackage)

//  def specs = cached("swaggerDef") {  //it would be beneficial to cache this endpoint as we do here, but it's not required if you don't expect much traffic.
//    Action.async { _ =>
//      Future.fromTry(generator.generate()).map(Ok(_)) //generate() can also taking in an optional arg of the route file name.
//    }
//  }

}