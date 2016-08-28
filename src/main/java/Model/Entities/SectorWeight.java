package Model.Entities;

public class SectorWeight {

    private String sector;
    private double weight;

    public SectorWeight(String sector, double weight) {
        this.sector = sector;
        this.weight = weight;
    }

    public String getSector() {
        return this.sector;
    }

    public double getWeight() {
        return this.weight;
    }
}
