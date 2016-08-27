package Model;

import Model.Entities.CountryWeight;
import Model.Entities.EtfData;
import Model.Entities.Holding;
import Model.Entities.SectorWeight;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by reclaimer on 8/22/16.
 */
@Configuration
public class LocalDataManager {

    private static final String DBURL = "jdbc:mysql://localhost:3306/ETF_DB?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "roo7CLAUD1tis8";

    //TODO Fix Select All statement
    private static final String CHECK_FOR_DATA = "SELECT * FROM basic_etf_data WHERE etf_name = (?) LIMIT 1";
    private static final String INSERT_BASE_ETF_DATA = "INSERT INTO basic_etf_data (etf_name, description) VALUES (?, ?)";
    private static final String INSERT_TOPTEN_HOLDINGS = "INSERT INTO top_ten_holdings (etf_ref, company, weight, shares) VALUES (?, ?, ?, ?)";
    private static final String INSERT_COUNTRY_WEIGHTS = "INSERT INTO country_weights (etf_ref, country_name, country_weight) VALUES (?, ?, ?)";
    private static final String INSERT_SECTOR_WEIGHTS = "INSERT INTO sector_weights (etf_ref, sector_name, sector_weight) VALUES (?, ?, ?)";
    private static final String SELECT_BASIC_ETF_DATA = "SELECT * FROM basic_etf_data WHERE etf_name = (?)";
    private static final String SELECT_TOPTEN_HOLDINGS= "SELECT * FROM top_top_holdings WHERE etf_ref = (?)";
    private static final String SELECT_COUNTRY_WEIGHTS = "SELECT * FROM sector_weights WHERE etf_ref = (?)";
    private static final String SELECT_SECTOR_WEIGHTS = "SELECT * FROM sector_weights WHERE etf_ref = (?)";

    public LocalDataManager() {}

    //TODO Break addEtfData method up
    //TODO Figure out why only basic_etf_data being added to database
    /**
     * Inserts data from an EtfData object into a database.
     * Multiple connections are required to accommodate for the multiple tables that data is inserted into.
     * @param etfObject The EtfData object that is to be inserted into the database
     */
    public void addEtfData(EtfData etfObject) {
        // Basic Etf data table
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {

            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(INSERT_BASE_ETF_DATA);
                statement.setString(1, etfObject.getName());
                statement.setString(2, etfObject.getDescription());
                statement.executeUpdate();
                System.out.println("Successfully added Basic Etf data to database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Top Ten Holdings data table
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(INSERT_TOPTEN_HOLDINGS);
                for (Holding holding : etfObject.getTopTenHoldings()) {
                    statement.setString(1, etfObject.getName());
                    statement.setString(2, holding.getCompany());
                    statement.setDouble(3, holding.getWeight());
                    statement.setInt(4, holding.getSharesHeld());
                    statement.executeUpdate();
                    System.out.println("Successfully added Top Ten Holdings data to data base");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Country Weights data table
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(INSERT_COUNTRY_WEIGHTS);
                for (CountryWeight countryWeight : etfObject.getCountryWeights()){
                    statement.setString(1, etfObject.getName());
                    statement.setString(2, countryWeight.getCountry());
                    statement.setDouble(3, countryWeight.getWeight());
                    statement.executeUpdate();
                    System.out.println("Successfully added Country Weights data to database");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Sector Weights data table
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(INSERT_SECTOR_WEIGHTS);
                for (SectorWeight sectorWeight : etfObject.getSectorWeights()){
                    statement.setString(1, etfObject.getName());
                    statement.setString(2, sectorWeight.getSector());
                    statement.setDouble(3, sectorWeight.getWeight());
                    statement.executeUpdate();
                    System.out.println("Successfully added Sector Weights data to database");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO Fix.
    private Connection getConnection(){
        Connection resultConn = null;
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                resultConn = conn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultConn;
    }

    //TODO Figure out how to build object properly
    /**
     * Retrieves a ETF data from the database and constructs a new EtfData object
     * @param etfSymbol The ETF that is being retrieved
     * @return An EtfData object
     */
    public EtfData getEtfData(String etfSymbol){
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(SELECT_ALL_ETF_DATA);
                statement.setString(1, etfSymbol);
                ResultSet etfData = statement.executeQuery();
                etfData.next();
                System.out.println("Etf Data: " + etfData);
                //return new EtfData(etfData.getString(1), etfData.getString(2), etfData.get)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks the database to see if a particular ETF exists or not
     * @param etfSymbol The ETF that is being searched for
     * @return A boolean: True, ETF exists; False, ETF does not exist
     */
    public boolean checkForData(String etfSymbol){
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

