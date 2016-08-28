package Model;

import Model.Entities.EtfData;
import Utils.CsvUtil;

import java.io.File;

/**
 * Created by reclaimer on 8/27/16.
 */
public class CsvManager {
    private String etfRef;
    private LocalDataManager localDataManager;

    public CsvManager(){};

    public File getCsv(String etfSymbol){
        EtfData etfObject = localDataManager.getEtfData(etfSymbol);
        return CsvUtil.createCsv(etfObject.getTopTenHoldings(), etfObject.getCountryWeights(), etfObject.getSectorWeights());
    }
}
