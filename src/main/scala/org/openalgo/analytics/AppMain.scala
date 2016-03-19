package org.openalgo.analytics

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives.{as, complete, decodeRequest, entity, path, post}
import akka.stream.ActorMaterializer
import argonaut.Argonaut.casecodec2
import com.typesafe.config.ConfigFactory
import de.heikoseeberger.akkahttpargonaut.ArgonautSupport

import scala.collection.JavaConverters._
import scala.collection.mutable

case class CovarianceInput(series1: mutable.Buffer[java.lang.Double], series2: mutable.Buffer[java.lang.Double])

object AppMain extends App with ArgonautSupport {
  val config = ConfigFactory.load()

  implicit def PersonCodecJson =
    casecodec2(CovarianceInput.apply, CovarianceInput.unapply)("series1", "series2")

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val route =
    path("covariance") {
      post {
        decodeRequest {
          entity(as[CovarianceInput]) { json: CovarianceInput =>
            complete(OK, CovarianceCall.getCovariance(json.series1.asJava, json.series2.asJava))
          }
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, config.getString("http.interface"), config.getInt("http.port"))

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  Console.readLine() // for the future transformations
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ ⇒ system.terminate()) // and shutdown when done
}