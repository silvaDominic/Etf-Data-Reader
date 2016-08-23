package Model;

/**
 * Created by reclaimer on 8/22/16.
 */
public class CountryWeight {

    private String country;
    private String weight;

    public CountryWeight(String country, String weight) {
        this.country = country;
        this.weight = weight;
    }

    public String getCountry() {
        return this.country;
    }

    public String getWeight() {
        return this.weight;
    }
}
