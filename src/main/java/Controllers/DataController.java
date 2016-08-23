package Controllers;


import Model.DataManager;
import Model.EtfData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by reclaimer on 8/22/16.
 */
@RestController
public class DataController {

    private String Url;
    private DataManager dataManager;

    public DataController(String Url){
        this.Url = Url;
        dataManager = new DataManager(Url);
    }

    @RequestMapping("/ETF")
    public ArrayList<EtfData> getAllEtfs(){
        return new ArrayList<EtfData>(dataManager.getAllEtfs());
    }

    @RequestMapping("/ETF/{ticker}")
    public EtfData getEtf(@PathVariable String ticker) {
        return new EtfData(ticker, dataManager.getEtfDescription(), dataManager.getTopTenHoldings(),
                           dataManager.getCountryWeights(), dataManager.getSectorWeights());
    }
}
