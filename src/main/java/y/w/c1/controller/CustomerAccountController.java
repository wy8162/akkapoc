package y.w.c1.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.OnComplete;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import y.w.c1.actor.CustomerAccountResourceActor;
import y.w.c1.configuration.SpringExtension;
import y.w.c1.message.CustomerAccountRequest;

@RestController
@RequestMapping("/api")
public class CustomerAccountController {
    final public static long MAX_TIMEOUT = 30l;

    @Autowired
    ActorSystem actorSystem;

    @Autowired
    SpringExtension springExtension;

    @GetMapping("/customer/{escid}/profile")
    public ResponseEntity<String> getCustomerAccounts(@PathVariable String escid, @RequestParam String profileReferenceId)
    {
        ActorRef actor = actorSystem.actorOf(springExtension.props(CustomerAccountResourceActor.class));

        actor.tell(new CustomerAccountRequest(escid, profileReferenceId), ActorRef.noSender());

        Timeout timeout = new Timeout(Duration.create(MAX_TIMEOUT, "seconds"));

        Future<Object> future = Patterns.ask(actor, new CustomerAccountRequest(escid, profileReferenceId), timeout);

        future.onComplete(new OnComplete<Object>() {
            @Override
            public void onComplete(Throwable failure, Object success) throws Throwable, Throwable {
                if (failure != null) {
                    if (failure.getMessage() != null) {

                    } else {

                    }
                } else {
                }
            }
        }, actorSystem.dispatcher());

        return new ResponseEntity(String.format("hi, %s, %s", escid, profileReferenceId), HttpStatus.OK);
    }
}
