package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by reclaimer on 8/22/16.
 */
public class SqlManager {

    private String username;
    private String password;
    private String dbUrl;
    private static final String SELECT_ETF = "SELECT * FROM etf_data WHERE id = (?)";
    private static final String CREATE_ETF = "INSERT INTO etf_data (id, etf_name, description)";
    private static final String JOIN_COUNTRY_WEIGHTS = "INSERT INTO etf_data (id, etf_name, description)";
    private static final String JOIN_SECTOR_WEIGHTS = "INSERT INTO etf_data (id, etf_name, description)";

    public SqlManager(String username, String password, String dbUrl) {
        this.username = username;
        this.password = password;
        this.dbUrl = dbUrl;
    }

    public String createEtfData(String etfName, String description, ArrayList<Holding> holdings, ArrayList<CountryWeight> countryWeights) {
        String id = "";
        try (Connection conn = DriverManager.getConnection(this.dbUrl, this.username, this.password)) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(CREATE_ETF);
                id = UUID.randomUUID().toString();
                statement.setString(1, id);
                statement.setString(2, etfName);
                statement.setString(3, description);
/*                statement.setString(4, holdings);
                statement.setString(3, description);*/
                statement.executeUpdate();
                System.out.println("Successfully added task to DB");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void getEtfData() {

    }

    public void getAllEtfData() {

    }
}
