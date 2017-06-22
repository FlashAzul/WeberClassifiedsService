package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("resourceController")
public class Application {

    public static void main(String[] args) {
        //This is a test commit
        SpringApplication.run(Application.class, args);
    }
}
