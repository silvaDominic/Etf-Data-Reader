package Model.Entities;

public class Holding {

    private String company;
    private double weight;
    private int shares;

    public Holding(String company, double weight, int shares){
        this.company = company;
        this.weight = weight;
        this.shares = shares;
    }

    public String getCompany() {
        return this.company;
    }

    public double getWeight() {
        return this.weight;
    }

    public int getShares() {
        return this.shares;
    }
}
