package controllers

import java.io.IOException

import com.github.dreamhead.moco.Moco
import com.github.dreamhead.moco.Moco._
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import play.api.libs.json.Json

class RESTControllerTest extends FunSuite with BeforeAndAfterEach with RESTStubServer {
  override def beforeEach() = {
    server
      .get(by(
        uri("/resource1")))
      .response(
        `with`(text("resource1")),
        header("Content-Type", "application/json")
      )

    server
      .get(and(
        by(uri("/resource2")),
        Moco.eq(query("id"), "1"),
        Moco.eq(query("uid"), "3")))
      .response(
        `with`(text(Json.stringify(Json.toJson(Map("id" -> 1, "uid" -> 3))))),
        //        toJson(Json.stringify(Json.toJson(Map("id" -> 1, "uid" -> 3)))),
        status(200),
        header("Content-Type", "application/json")
      )

    server
      .post(and(
        by(uri("/resource3")),
        json(text("{\"id\":1, \"price\":10}"))))
      .response(
        `with`(text(Json.stringify(Json.toJson(Map("id" -> 1, "price" -> 10))))),
        status(201),
        header("Content-Type", "application/json")
      )

    mocoRunner.start()
    super.beforeEach()
  }

  override def afterEach() = {
    mocoRunner.stop()
  }

  test("testPublicResource") {
    Thread.sleep(50000)
    assert(scala.io.Source.fromURL("http://localhost:12306/resource1").mkString == "resource1")
    assertThrows[IOException](scala.io.Source.fromURL("http://localhost:12306/resource2").mkString)
    assert(scala.io.Source.fromURL("http://localhost:12306/resource2?id=1&uid=3").mkString == "{\"id\":1,\"uid\":3}")
  }

}
