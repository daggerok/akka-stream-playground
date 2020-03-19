package daggerok

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object Main extends App {

  sealed trait Command
  case class SayHello(whom: String) extends Command
  case class SayGoodbye(whom: String) extends Command

  object Process {
    def apply(): Behavior[Command] = Behaviors.setup { ctx =>
      Behaviors.receiveMessage {
        case SayHello(name) =>
          ctx.log.info(s"Hello, {}!", name)
          Behaviors.same
        case SayGoodbye(name) =>
          ctx.log.info("bb, {}...", name)
          Behaviors.same
      }
    }
  }

  val app = ActorSystem(Process(), "playground")
  app ! SayHello("Maksimko!")
  app.terminate()
}
