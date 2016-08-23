package Model;

import java.util.ArrayList;

/**
 * Created by reclaimer on 8/22/16.
 */
public class DataManager {

    private String Url;
    private HtmlParser htmlParser;
    private SqlManager sqlManager;

    public DataManager(String Url){
        this.Url = Url;
        htmlParser = new HtmlParser(Url);
        //sqlManager = new SqlManager();
    }

    public String getEtfDescription() {
        return htmlParser.parseDescription();
    }

    public ArrayList<Holding> getTopTenHoldings(){
        return htmlParser.parseTopTenHoldings();
    }

    public ArrayList<CountryWeight> getCountryWeights(){
        return htmlParser.parseCountryWeights();
    }

    public ArrayList<SectorWeight> getSectorWeights(){
        return htmlParser.parseSectorWeights();
    }

    public ArrayList<EtfData> getAllEtfs(){
        return null;
    }
}
