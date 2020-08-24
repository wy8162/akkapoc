package y.w.c1.configuration;

import akka.actor.AbstractLoggingActor;
import akka.actor.Extension;
import akka.actor.Props;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Singleton, one per Actor System.
 */
@Component
public class SpringExtension implements Extension {

    private ApplicationContext applicationContext;

    public void initialize(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Props props(Class<? extends AbstractLoggingActor> actorBeanClass) {
        return Props.create(SpringActorProducer.class, applicationContext, actorBeanClass);
    }

    public Props props(Class<? extends AbstractLoggingActor> actorBeanClass, Object[] parameters) {
        return Props.create(SpringActorProducer.class, applicationContext, actorBeanClass, parameters);
    }
}