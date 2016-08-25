package App;

import Controllers.DataController;
import Model.LocalDataManager;
import Utils.CsvUtil;
import Model.RemoteDataManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by reclaimer on 8/19/16.
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