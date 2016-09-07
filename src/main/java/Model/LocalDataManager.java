package Model;

import Model.Entities.*;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.ArrayList;

@Configuration
public class LocalDataManager {

    private static final String DBURL = "jdbc:mysql://localhost:3306/ETF_DB?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "roo7CLAUD1tis8";

    //TODO Fix Select All statement
    private static final String CHECK_FOR_DATA = "SELECT * FROM basic_etf_data WHERE etf_symbol = (?) LIMIT 1";
    private static final String INSERT_BASIC_ETF_DATA = "INSERT INTO basic_etf_data (etf_symbol, description) VALUES (?, ?)";
    private static final String INSERT_TOPTEN_HOLDINGS = "INSERT INTO top_ten_holdings (etf_ref, company, weight, shares) VALUES (?, ?, ?, ?)";
    private static final String INSERT_COUNTRY_WEIGHTS = "INSERT INTO country_weights (etf_ref, country_name, country_weight) VALUES (?, ?, ?)";
    private static final String INSERT_SECTOR_WEIGHTS = "INSERT INTO sector_weights (etf_ref, sector_name, sector_weight) VALUES (?, ?, ?)";
    private static final String SELECT_DESCRIPTION = "SELECT description FROM basic_etf_data WHERE etf_symbol = (?)";
    private static final String SELECT_TOPTEN_HOLDINGS= "SELECT * FROM top_ten_holdings WHERE etf_ref = (?)";
    private static final String SELECT_COUNTRY_WEIGHTS = "SELECT * FROM country_weights WHERE etf_ref = (?)";
    private static final String SELECT_SECTOR_WEIGHTS = "SELECT * FROM sector_weights WHERE etf_ref = (?)";

    LocalDataManager() {}

    //TODO Break addEtfData method up
    //TODO Figure out why only basic_etf_data being added to database
    /**
     * Adds ETF data into a database.
     * Multiple connections are required to accommodate for the multiple tables that data is inserted into.
     * @param etfObject The EtfData object that is to be inserted into the database.
     */
    void addEtfData(EtfData etfObject) {
        insertBasicEtfData(etfObject);
        insertTopTenHoldingData(etfObject);
        insertCountryWeightData(etfObject);
        insertInsertSectorWeightData(etfObject);
    }

    /**
     * Retrieves ETF data from a database
     * @param etfSymbol The ETF symbol that data is being requested on.
     * @return A EtfData object
     */
    EtfData getEtfData(String etfSymbol){
        return new EtfData(etfSymbol,
                           fetchDescription(etfSymbol),
                           fetchTopTenHoldingData(etfSymbol),
                           fetchCountryWeightData(etfSymbol),
                           fetchSectorWeightData(etfSymbol));
    }

    /**
     * Inserts basic etf data into a database.
     * @param etfObject The EtfData object that is data is being stored.
     */
    private void insertBasicEtfData(EtfData etfObject){
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {

            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(INSERT_BASIC_ETF_DATA);
                statement.setString(1, etfObject.getSymbol());
                statement.setString(2, etfObject.getDescription());
                statement.executeUpdate();
                System.out.println("Successfully ADDED Basic Etf Data to database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts top ten holding data into a database.
     * @param etfObject The EtfData object that is data is being stored.
     */
    private void insertTopTenHoldingData(EtfData etfObject) {
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(INSERT_TOPTEN_HOLDINGS);
                for (Holding holding : etfObject.getTopTenHoldings()) {
                    statement.setString(1, etfObject.getSymbol());
                    statement.setString(2, holding.getCompany());
                    statement.setDouble(3, holding.getWeight());
                    statement.setInt(4, holding.getShares());
                    statement.executeUpdate();
                    System.out.println("Successfully ADDED Top Ten Holdings data to data base");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts country weight data into a database.
     * @param etfObject The EtfData object that is data is being stored.
     */
    private void insertCountryWeightData(EtfData etfObject) {
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(INSERT_COUNTRY_WEIGHTS);
                for (CountryWeight countryWeight : etfObject.getCountryWeights()) {
                    statement.setString(1, etfObject.getSymbol());
                    statement.setString(2, countryWeight.getCountry());
                    statement.setDouble(3, countryWeight.getWeight());
                    statement.executeUpdate();
                    System.out.println("Successfully ADDED Country Weights data to database");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts sector weight data into a database.
     * @param etfObject The EtfData object that is data is being stored.
     */
    private void insertInsertSectorWeightData(EtfData etfObject){
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(INSERT_SECTOR_WEIGHTS);
                for (SectorWeight sectorWeight : etfObject.getSectorWeights()){
                    statement.setString(1, etfObject.getSymbol());
                    statement.setString(2, sectorWeight.getSector());
                    statement.setDouble(3, sectorWeight.getWeight());
                    statement.executeUpdate();
                    System.out.println("Successfully ADDED Sector Weights data to database");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches the basic ETF data from a database and creates a new EtfData object from it.
     * @param etfSymbol The EtfData object that is used to fetch basic etf data.
     * @return An EtfData object
     */
    private String fetchDescription(String etfSymbol){
        String description = "";
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(SELECT_DESCRIPTION);
                statement.setString(1, etfSymbol);
                ResultSet result = statement.executeQuery();
                if(result.next()){
                    description = result.getString(1);
                }
                System.out.println("Successfully FETCHED Description from database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return description;
    }

    /**
     * Fetches the top ten holding data from a database and creates a new array list of holdings.
     * @param etfSymbol The EtfData object that is used to fetch top ten holding data.
     * @return An array list of holdings
     */
    private ArrayList<Holding> fetchTopTenHoldingData(String etfSymbol){
        ArrayList<Holding> topTenHoldingData = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(SELECT_TOPTEN_HOLDINGS);
                statement.setString(1, etfSymbol);
                ResultSet result = statement.executeQuery();
                while (result.next()){
                    topTenHoldingData.add(new Holding(result.getString(2), result.getDouble(3), result.getInt(4)));
                }
                System.out.println("Successfully FETCHED Top Ten Holding data from database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topTenHoldingData;
    }

    /**
     * Fetches the country weight data from a database and creates a new array list of country weights.
     * @param etfSymbol The EtfData object that is used to fetch country weight data.
     * @return An array list of country weights
     */
    private ArrayList<CountryWeight> fetchCountryWeightData(String etfSymbol){
        ArrayList<CountryWeight> countryWeightData = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(SELECT_COUNTRY_WEIGHTS);
                statement.setString(1, etfSymbol);
                ResultSet result = statement.executeQuery();
                while (result.next()){
                    countryWeightData.add(new CountryWeight(result.getString(2), result.getDouble(3)));
                }
                System.out.println("Successfully FETCHED Country Weight data from database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryWeightData;
    }

    /**
     * Fetches the sector weight data from a database and creates a new array list of sector weights.
     * @param etfName The EtfData object that is used to fetch sector weight data.
     * @return An array list of secrot weights
     */
    private ArrayList<SectorWeight> fetchSectorWeightData(String etfName){
        ArrayList<SectorWeight> sectorWeights = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(SELECT_SECTOR_WEIGHTS);
                statement.setString(1, etfName);
                ResultSet result = statement.executeQuery();
                while(result.next()){
                    sectorWeights.add(new SectorWeight(result.getString(2), result.getDouble(3)));
                }
                System.out.println("Successfully FETCHED Sector Weight data from database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectorWeights;
    }


    /**
     * Checks the database to see if a particular ETF exists or not.
     * @param etfSymbol The ETF that is being searched for.
     * @return A boolean: True, ETF exists; False, ETF does not exist
     */
    boolean checkForData(String etfSymbol){
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(CHECK_FOR_DATA);
                statement.setString(1, etfSymbol);
                ResultSet result = statement.executeQuery();
                if (!result.isBeforeFirst()){
                    System.out.println("No data currently in database for this ETF");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Data already exists in database for this ETF");
        return true;
    }
}

