package Model.Entities;

/**
 * Created by reclaimer on 8/22/16.
 */
public class Holding {

    private String company;
    private double weight;
    private int sharesHeld;

    public Holding(String company, double weight, int sharesHeld){
        this.company = company;
        this.weight = weight;
        this.sharesHeld = sharesHeld;
    }

    public String getCompany() {
        return this.company;
    }

    public double getWeight() {
        return this.weight;
    }

    public int getSharesHeld() {
        return this.sharesHeld;
    }
}
