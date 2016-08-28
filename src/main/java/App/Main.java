package App;

import Controllers.DataController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Runs Spring Boot Starter Security based app and instantiates DataController
 * A Component Scan is run on the relevant packages
 */
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = {"Config", "Controllers", "Model"})
public class Main {
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(App.Main.class, args);
        new DataController();
    }

    //TODO Implement AutoWiring functionality
}