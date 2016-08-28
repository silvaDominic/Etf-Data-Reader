package Model;

import Model.Entities.EtfData;
import Utils.CsvUtil;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by reclaimer on 8/27/16.
 */
public class CsvManager {
    private String etfRef;
    private LocalDataManager localDataManager;

    public CsvManager(){};

    //TODO
    public File getCsv(String etfSymbol, HttpServletResponse response){
        EtfData etfObject = localDataManager.getEtfData(etfSymbol);
        return CsvUtil.createCsv(etfObject.getTopTenHoldings(), etfObject.getCountryWeights(), etfObject.getSectorWeights());
    }
}
