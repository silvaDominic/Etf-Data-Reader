package Model;

/**
 * Created by reclaimer on 8/22/16.
 */
public class Holding {

    private String company;
    private String weight;
    private String sharesHeld;

    public Holding(String company, String weight, String sharesHeld){
        this.company = company;
        this.weight = weight;
        this.sharesHeld = sharesHeld;
    }

    public Holding(){}

    public String getCompany() {
        return this.company;
    }

    public String getWeight() {
        return this.weight;
    }

    public String getSharesHeld() {
        return this.sharesHeld;
    }
}
