package y.w.c1.actor;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.SmallestMailboxRoutingLogic;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import y.w.c1.configuration.Actor;
import y.w.c1.configuration.SpringExtension;
import y.w.c1.message.CreditCardRequest;
import y.w.c1.message.CustomerAccountRequest;
import y.w.c1.message.ServiceTimout;

@Actor
public class CustomerAccountOrchestrationActor extends AbstractLoggingActor  {
    public static int MAX_ACTORS = 5;
    public static long CREDIT_CARD_TIMEOUT = 30l;

    @Autowired
    private SpringExtension springExtension;

    private Router router;

    @Override
    public void preStart() throws Exception {
        log().debug("Creating pool of Credit Card Actors");

        router = new Router(new SmallestMailboxRoutingLogic(), createRoutees());

        super.preStart();
    }

    protected List<Routee> createRoutees() {
        List<Routee> routees = new ArrayList<>();
        for (int i = 0; i < MAX_ACTORS; i++) {
            ActorRef actor = getContext().actorOf(springExtension.props(CreditCardActor.class));
            getContext().watch(actor);
            routees.add(new ActorRefRoutee(actor));
        }
        return routees;
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        log().debug("Inside postStop");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CreditCardRequest.class, m -> router.route(m, getSender()))
                .match(Terminated.class, m -> createAnActor(m))
                .match(ServiceTimout.class, m -> log().error("Timed out"))
                .matchAny(this::unhandled)
                .build();
    }

    @Override
    public void unhandled(Object message) {
        super.unhandled(message);

        log().error("Not handled : " + message.toString());
    }

    private void scheduleTaskFinished() {
        getContext()
                .system()
                .scheduler()
                .scheduleOnce(Duration.ofSeconds(CREDIT_CARD_TIMEOUT), self(), ServiceTimout.Instance,
                        getContext().dispatcher(), self());
    }

    private void createAnActor(Object message) {
        router = router.removeRoutee(((Terminated) message).actor());
        ActorRef actor = getContext().actorOf(springExtension.props(CreditCardActor.class));
        getContext().watch(actor);
        router = router.addRoutee(new ActorRefRoutee(actor));
    }
}
