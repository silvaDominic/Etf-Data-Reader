package Controllers;

import Model.CsvManager;
import Model.Entities.EtfData;
import Model.EtfDataManager;
import org.springframework.web.bind.annotation.*;

@RestController
public class DataController {

    private EtfDataManager etfDataManager;
    private CsvManager csvManager;

    public DataController(){
        this.etfDataManager = new EtfDataManager();
    }

    /**
     * Mapping for particular ETF.
     * Retrieves relevant ETF data.
     * @param etfSymbol The ETF that data is being request on.
     * @return An EtfData object that will be converted to JSON.
     */
    @RequestMapping(value="/ETF/{etfSymbol}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody EtfData getEtf(@PathVariable String etfSymbol) {
        return etfDataManager.getEtfData(etfSymbol);
    }
}
