package Model;

import java.util.ArrayList;

/**
 * Created by reclaimer on 8/23/16.
 */
public class EtfData {

    private String name;
    private String description;
    private ArrayList<Holding> topTenHoldings;
    private ArrayList<CountryWeight> countryWeights;
    private ArrayList<SectorWeight> sectorWeights;

    public EtfData(String name, String description, ArrayList<Holding> topTenHoldings,
                   ArrayList<CountryWeight> countryWeights, ArrayList<SectorWeight> sectorWeights) {
        this.name = name;
        this.description = description;
        this.topTenHoldings = topTenHoldings;
        this.countryWeights = countryWeights;
        this.sectorWeights = sectorWeights;
    }

    public ArrayList<CountryWeight> getCountryWeights() {
        return countryWeights;
    }

    public ArrayList<SectorWeight> getSectorWeights() {
        return sectorWeights;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Holding> getTopTenHoldings() {
        return topTenHoldings;
    }
}
