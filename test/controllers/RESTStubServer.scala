package controllers

import com.github.dreamhead.moco.Moco.httpServer
import com.github.dreamhead.moco.Runner.runner
import com.github.dreamhead.moco.{HttpServer, Runner}
import org.scalatest.{BeforeAndAfter, Suite}

trait RESTStubServer {
  val server: HttpServer = httpServer(12306)
  val mocoRunner: Runner = runner(server)
}
