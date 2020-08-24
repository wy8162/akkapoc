package y.w.c1.actor;

import akka.actor.AbstractLoggingActor;
import y.w.c1.configuration.Actor;
import y.w.c1.message.CustomerAccountRequest;

@Actor
public class EntitlementActor extends AbstractLoggingActor  {
    public EntitlementActor() {}

    @Override
    public void preStart() throws Exception {
        super.preStart();
        log().debug("Inside preStart");
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        log().debug("Inside postStop");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CustomerAccountRequest.class, o -> log().info("Customer Request "))
                .matchAny(this::unhandled)
                .build();
    }

    @Override
    public void unhandled(Object message) {
        super.unhandled(message);

        log().error("Not handled : " + message.toString());
    }
}
