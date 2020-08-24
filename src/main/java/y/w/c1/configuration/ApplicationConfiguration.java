package y.w.c1.configuration;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"y.w.c1.controller", "y.w.c1.service", "y.w.c1.actor"})
public class ApplicationConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SpringExtension springExtension;

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("CustomerAccountAkkaSystem", akkaConfiguration());
        springExtension.initialize(applicationContext);
        return system;
    }

    /**
     * Load AKKA configurations from src/main/resources/application.conf
     * @return
     */
    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }
}
