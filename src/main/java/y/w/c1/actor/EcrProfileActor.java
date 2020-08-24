package y.w.c1.actor;

import akka.actor.AbstractActorWithTimers;
import akka.actor.AbstractLoggingActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.time.Duration;
import y.w.c1.configuration.Actor;
import y.w.c1.message.CustomerAccountRequest;
import y.w.c1.message.ServiceTimout;
import y.w.c1.pojo.Timeout;

@Actor
public class EcrProfileActor extends AbstractActorWithTimers {
    public static long ECR_TIMEOUT = 30l;
    public static String ECR_TIMER_KEY = "ECRTimer";

    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public EcrProfileActor() {}

    @Override
    public void preStart() throws Exception {
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CustomerAccountRequest.class, m -> log.info("Customer Request "))
                .match(ServiceTimout.class, m -> log.info("Timeout"))
                .matchAny(this::unhandled)
                .build();
    }

    @Override
    public void unhandled(Object message) {
        super.unhandled(message);

        log.error("Not handled : " + message.toString());
    }

    protected void startTimer() {
        getTimers().startSingleTimer(ECR_TIMER_KEY, ServiceTimout.Instance, Duration.ofSeconds(ECR_TIMEOUT));
    }
}
