package Model;

/**
 * Created by reclaimer on 8/22/16.
 */
public class SectorWeight {

    private String sector;
    private String weight;

    public SectorWeight(String sector, String weight) {
        this.sector = sector;
        this.weight = weight;
    }

    public String getSector() {
        return this.sector;
    }

    public String getWeight() {
        return this.weight;
    }
}
