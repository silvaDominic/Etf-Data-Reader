package App;

import Controllers.DataController;
import Model.LocalDataManager;
import Utils.CsvUtil;
import Model.RemoteDataManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by reclaimer on 8/19/16.
 */
@EnableAutoConfiguration
@SpringBootApplication
public class Main {
    public static void main(String[] args) throws Throwable {
        //SpringApplication.run(App.Main.class, args);
        new DataController();
    }
}


//TODO Finish implementing Csv Util class
//TODO Create html file for Fund Sector Allocation and implement parse method
//TODO Consult yousuf about structure of project (Url, LocalDataManager, DataManager)
//TODO Add data to DB