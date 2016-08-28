package Model.Entities;

public class BasicEtfData {
    private String symbol;
    private String description;

    public BasicEtfData(String symbol, String description){
        this.symbol = symbol;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getSymbol() {
        return symbol;
    }
}
