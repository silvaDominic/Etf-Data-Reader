package Model;

import Model.Entities.CountryWeight;
import Model.Entities.EtfData;
import Model.Entities.Holding;
import Model.Entities.SectorWeight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by reclaimer on 8/21/16.
 */
public class RemoteDataManager {

    private static final String baseUrl = "https://www.spdrs.com/product/fund.seam?ticker=";
    private static final String TOP_HOLDINGS = "#FUND_TOP_HOLDINGS";
    private static final String FUND_COUNTRY_WEIGHTS = "#FUND_COUNTRY_WEIGHTS";
    private static final String FUND_SECTOR = "#FUND_SECTOR";
    private static final String DESCRIPTION = ".overview";

    public RemoteDataManager(){}

    /**
     * Parses Top Ten Holdings table data from a website
     * @param etfSymbol The ETF that data will be parsed on
     * @return An array list of Holding objects
     */
    public ArrayList<Holding> parseTopTenHoldings(String etfSymbol){
        String url = baseUrl + etfSymbol;
        ArrayList<Holding> holdings = new ArrayList<>(); // Instantiation of ArrayList
        try {
            // Timeout is extended to accommodate for lengthy parses
            Document doc = Jsoup.connect(url).timeout(10000).get();
            Elements topHoldings = doc.select(TOP_HOLDINGS);

            for(Element e : topHoldings.select("table").select("tr")){
                if (!e.select("td").isEmpty()) {
                    ArrayList<Element> list = e.select("td");
                    String companyName = list.get(0).text();
                    String weight = removeSymbols(list.get(1).text());
                    String sharedHeld = removeSymbols(list.get(2).text());
                    holdings.add(new Holding(companyName, Double.parseDouble(weight), Integer.parseInt(sharedHeld)));
                }
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
        return holdings;
    }

    private String removeSymbols(String data){
        String result = "";
        String delim = "[%,]+";
        String[] parsedData = data.split(delim);
        for (String d : parsedData){
            result = d;
        }
        return result;
    }

    /**
     * Parses all country weight table data from a website
     * @param etfSymbol The ETF that data will be parsed on
     * @return An array list of CountryWeight objects
     */
    public ArrayList<CountryWeight> parseCountryWeights(String etfSymbol){
        String url = baseUrl + etfSymbol;
        ArrayList<CountryWeight> ctryWeights = new ArrayList<>();
        try {
            // Timeout is extended to accommodate for lengthy parses
            Document doc = Jsoup.connect(url).timeout(10000).get();
            Elements countryWeights = doc.select(FUND_COUNTRY_WEIGHTS);

                for(Element e : countryWeights.select("table").select("tr")){
                    if (!e.select("td").isEmpty()) {
                        ArrayList<Element> list = e.select("td");
                        String countryName = list.get(0).text();
                        String weight = removeSymbols(list.get(1).text());
                        ctryWeights.add(new CountryWeight(countryName, Double.parseDouble(weight)));
                    }
                }
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
        return ctryWeights;
    }

    //TODO Finish implementing sample case

    /**
     * Parses all sector weight table data from a website
     * @param etfSymbol The ETF that data will be parsed on
     * @return An array list of SectorWeight objects
     */
    public ArrayList<SectorWeight> parseSectorWeights(String etfSymbol){
        String url = baseUrl + etfSymbol;
        ArrayList<SectorWeight> sectWeights = new ArrayList<>();
        try {
            // Timeout is extended to accommodate for lengthy parses
            Document doc = Jsoup.connect(url).timeout(10000).get();
            Elements sectorWeights = doc.select(FUND_SECTOR);

            for(Element e : sectorWeights.select("div.chart_legend").select("tr")){
                if (!e.select("td").isEmpty()) {
                    ArrayList<Element> list = e.select("td");
                    String sector = list.get(1).text();
                    String weight = removeSymbols(list.get(1).text());
                    sectWeights.add(new SectorWeight(sector, Double.parseDouble(weight)));
                }
                System.out.println(e.text());
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
        return sectWeights;
    }

    /**
     * Parse the ETF description from a website
     * @param etfSymbol The ETF that data will be parsed on
     * @return A string representing the ETF description
     */
    public String parseDescription(String etfSymbol){
        String url = baseUrl + etfSymbol;
        String descriptionText = "";
        try {
            // Timeout is extended to accommodate for lengthy parses
            Document doc = Jsoup.connect(url).timeout(10000).get();
            Elements sectorWeights = doc.select(DESCRIPTION);

            Element descriptionElem = sectorWeights.select(".objective").select("p").first();
            descriptionText =  descriptionElem.text();

        } catch (IOException e) {
            System.out.println("Error" + e);
        }
        return descriptionText;
    }

    /**
     * Creates and returns an EtfData object from parsed data gathered from a website
     * @param etfSymbol
     * @return an EtfData object
     */
    public EtfData getEtfObject(String etfSymbol){
        return new EtfData(etfSymbol,
                           parseDescription(etfSymbol),
                           parseTopTenHoldings(etfSymbol),
                           parseCountryWeights(etfSymbol),
                           parseSectorWeights(etfSymbol));
    }
}