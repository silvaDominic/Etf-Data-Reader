package Model.Entities;

import java.util.ArrayList;

public class EtfData {

    private String symbol;
    private String description;
    private ArrayList<Holding> topTenHoldings;
    private ArrayList<CountryWeight> countryWeights;
    private ArrayList<SectorWeight> sectorWeights;

    public EtfData(String symbol, String description, ArrayList<Holding> topTenHoldings,
                   ArrayList<CountryWeight> countryWeights, ArrayList<SectorWeight> sectorWeights) {
        this.symbol = symbol;
        this.description = description;
        this.topTenHoldings = topTenHoldings;
        this.countryWeights = countryWeights;
        this.sectorWeights = sectorWeights;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<CountryWeight> getCountryWeights() {
        return countryWeights;
    }

    public ArrayList<SectorWeight> getSectorWeights() {
        return sectorWeights;
    }

    public ArrayList<Holding> getTopTenHoldings() {
        return topTenHoldings;
    }
}
