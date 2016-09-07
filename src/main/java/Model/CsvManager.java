package Model;

import Model.Entities.EtfData;
import Utils.CsvUtil;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by reclaimer on 8/27/16.
 */
@Component
public class CsvManager {
    private LocalDataManager localDataManager;

    public CsvManager(){
        this.localDataManager = new LocalDataManager();
    }

    //TODO
    public void getCsv(String etfSymbol, HttpServletResponse response){
        EtfData etfObject = localDataManager.getEtfData(etfSymbol);
        CsvUtil.createCsv(etfObject, response);
    }
}
