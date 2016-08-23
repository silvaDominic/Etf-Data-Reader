package App;

import Controllers.DataController;
import Utils.CsvUtil;
import Model.HtmlParser;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by reclaimer on 8/19/16.
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) throws Throwable {
        //SpringApplication.run(App.Main.class, args);
        String Url = "https://www.spdrs.com/product/fund.seam?ticker=";
        new DataController(Url);

        // Test
        HtmlParser htmlParser = new HtmlParser(Url + "ACIM");
        CsvUtil.createCsv(htmlParser.parseTopTenHoldings(), htmlParser.parseCountryWeights(), null);
    }
}

//TODO Finish implementing Csv Util class
//TODO Create html file for Fund Sector Allocation and implement parse method
//TODO Consult yousuf about structure of project (Url, SqlManager, DataManager)
//TODO Add data to DB