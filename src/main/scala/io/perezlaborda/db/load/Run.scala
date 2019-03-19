package io.perezlaborda.db.load

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.slick.scaladsl._
import akka.stream.scaladsl._
import com.danielasfregola.randomdatagenerator.RandomDataGenerator._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.duration._

object Run extends App {

  implicit val system = ActorSystem("loader")
  implicit val mat = ActorMaterializer()
  implicit val session = SlickSession.forConfig("benchmark")
  implicit val ec = system.dispatcher
  system.registerOnTermination(() => session.close())

  case class UserEvent(userId: String, datetime: Long, countryCode: String, data: String)

  val s = Source(1 to 5000)
    .throttle(1, 10.millis)
    .map{ _ =>
      val now = System.currentTimeMillis()
      val txt = random[String](128).mkString(" ")
      UserEvent("352E0E15-C3FA-2F74-38B6-1FDD4E86CE8C",now,"Argentina",txt)
    }.runWith{
      Slick.sink{ user =>
        sqlu"INSERT INTO bench.events (user_id,datetime,country_code,data) VALUES ('352E0E15-C3FA-2F74-38B6-1FDD4E86CE8C', ${user.datetime}, 'Argentina', ${user.data})"
      }
    }

  s.foreach{ d =>
    println(d)
    system.terminate()
  }

}
