package y.w.c1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("y.w.c1.configuration")
public class AkkapocApplication {

	public static void main(String[] args) {
		SpringApplication.run(AkkapocApplication.class, args);
	}

}
