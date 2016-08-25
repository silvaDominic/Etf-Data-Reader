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

    private static final String DBURL = "jdbc:mysql://localhost:3306/TaskDB?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static final String SELECT_ALL_ETF_DATA = "SELECT basic_etf_data*, top_ten_holdings*, country_weights*, sector_weights* FROM basic_etf_data JOIN top_ten_holdings ON top_ten_holdings.etf_ref = basic_etf_data.etf_name JOIN country_weights ON country_weights.ref_name = top_ten_holdings.ref_name JOIN sector_weights ON sector_weights.ref_name = basic_etf_data.etf_name WHERE basic_etf_data.etf_name = (?)";
    private static final String INSERT_BASE_ETF_DATA = "INSERT INTO basic_etf_data (etf_name, description)";
    private static final String INSERT_TOPTEN_HOLDINGS = "INSERT INTO top_ten_holdings (etf_ref, company, weight, shares)";
    private static final String INSERT_COUNTRY_WEIGHTS = "INSERT INTO country_weights (etf_ref, country_name, country_weight)";
    private static final String INSERT_SECTOR_WEIGHTS = "INSERT INTO sector_weights (etf_ref, sector_name, sector_weight)";
    private static final String CHECK_FOR_DATA = "SELECT EXISTS(SELECT * FROM basic_etf_data WHERE etf_name = (?)";

    public LocalDataManager() {}

    public void addEtfData(EtfData etfObject) {
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(INSERT_BASE_ETF_DATA);
                statement.setString(1, etfObject.getName());
                statement.setString(2, etfObject.getDescription());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(INSERT_TOPTEN_HOLDINGS);
                for (Holding holding : etfObject.getTopTenHoldings()) {
                    statement.setString(1, etfObject.getName());
                    statement.setString(2, holding.getCompany());
                    statement.setDouble(3, holding.getWeight());
                    statement.setInt(4, holding.getSharesHeld());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(INSERT_COUNTRY_WEIGHTS);
                for (CountryWeight countryWeight : etfObject.getCountryWeights()){
                    statement.setString(1, etfObject.getName());
                    statement.setString(2, countryWeight.getCountry());
                    statement.setDouble(3, countryWeight.getWeight());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(INSERT_SECTOR_WEIGHTS);
                for (SectorWeight sectorWeight : etfObject.getSectorWeights()){
                    statement.setString(1, etfObject.getName());
                    statement.setString(2, sectorWeight.getSector());
                    statement.setDouble(3, sectorWeight.getWeight());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EtfData getEtfData(String etfName){
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(SELECT_ALL_ETF_DATA);
                statement.setString(1, etfName);
                ResultSet etfData = statement.executeQuery();
                etfData.next();
                System.out.println(etfData);
                //return new EtfData(etfData.getString(1), etfData.getString(2), etfData.get)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkForData(String etfSymbol){
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(CHECK_FOR_DATA);
                statement.setString(1, etfSymbol);
                ResultSet result = statement.executeQuery();
                if (result.next()){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
