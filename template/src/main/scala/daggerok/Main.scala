package daggerok

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}

object Main extends App {
  implicit val app = ActorSystem("playground")

  Source.single("ololo trololo")
    .map(_.toUpperCase)
    .to(Sink.foreach(println))
    .run()

  app.terminate()
}
