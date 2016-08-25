package Model.Entities;

/**
 * Created by reclaimer on 8/22/16.
 */
public class CountryWeight {

    private String country;
    private double weight;

    public CountryWeight(String country, double weight) {
        this.country = country;
        this.weight = weight;
    }

    public String getCountry() {
        return this.country;
    }

    public double getWeight() {
        return this.weight;
    }
}
