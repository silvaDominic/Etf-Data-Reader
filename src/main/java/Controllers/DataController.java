package Controllers;


import Model.*;
import Model.Entities.EtfData;
import org.springframework.web.bind.annotation.*;

/**
 * Created by reclaimer on 8/22/16.
 */
@RestController
public class DataController {

    private EtfDataManager etfDataManager;

    public DataController(){
        this.etfDataManager = new EtfDataManager();
    }

    @RequestMapping(value="/ETF/{ticker}", method=RequestMethod.GET)
    public EtfData getEtf(@PathVariable String ticker) {
        return etfDataManager.getEtfData(ticker);
    }
}