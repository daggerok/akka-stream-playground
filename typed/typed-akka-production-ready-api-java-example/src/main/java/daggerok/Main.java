package daggerok;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.Value;

import java.util.function.Supplier;

public class Main {
  public interface Command {}

  @Value(staticConstructor = "to")
  public static class SayHello implements Command {
    String whom;
  }

  public static class Process extends AbstractBehavior<Command> {

    public static Supplier<Behavior<Command>> behavior = () -> Behaviors.setup(Process::new);

    public Process(ActorContext<Command> context) {
      super(context);
    }

    @Override
    public Receive<Command> createReceive() {
      return newReceiveBuilder()
          .onMessage(SayHello.class, this::onSayHello)
          .build();
    }

    private Behavior<Command> onSayHello(SayHello cmd) {
      getContext().getLog().info("Hola {}! from {}", cmd.getWhom(), cmd);
      return this;
    }
  }

  public static void main(String[] args) {
    ActorSystem<Command> system = ActorSystem.create(Process.behavior.get(), "playground");
    system.tell(SayHello.to("Maksimko"));
    system.terminate();
  }
}
