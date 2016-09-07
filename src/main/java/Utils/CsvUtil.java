package Utils;

import Model.Entities.CountryWeight;
import Model.Entities.EtfData;
import Model.Entities.Holding;
import Model.Entities.SectorWeight;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

//TODO Figure out how files are built and downloaded with a server side ref.
@Component
public class CsvUtil {

    //TODO Implement with logic if possible
    /**
     * Creates a CSV file of relevant ETF data.
     * A file is first created to allow for appending only data that exists.
     * @param etfObject The EtfData object that supplies data for CSV creation
     * @param response An HttpResponse used to set content type and headers
     */
    public static void createCsv(EtfData etfObject, HttpServletResponse response) {
        String fileName = etfObject.getSymbol() + ".csv";
        ArrayList<Holding> holdings = etfObject.getTopTenHoldings();
        ArrayList<CountryWeight> countryWeights = etfObject.getCountryWeights();
        ArrayList<SectorWeight> sectorWeights = etfObject.getSectorWeights();

        String[] topTenHoldingsHeaders = {"company", "weight", "shares"};
        String[] countryWeightsHeaders = {"country", "weight"};
        String[] sectorWeightsHeaders = {"sector", "weight"};

        try {
            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

            // If data exists add it to the CSV file
            if (holdings != null) {
                AppendTopTenHoldings(csvWriter, holdings, topTenHoldingsHeaders);
            }
            if (countryWeights != null) {
                AppendCountryWeights(csvWriter, countryWeights, countryWeightsHeaders);
            }
            if (sectorWeights != null) {
                AppendSectorWeights(csvWriter, sectorWeights, sectorWeightsHeaders);
            }

            response.setContentType("text/csv");

            //Set headers
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\" ", fileName);
            response.setHeader(headerKey, headerValue);

            csvWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //TODO Learn more about how writers work
    /**
     * Appends Top Ten Holding data to an existing CSV file
     * @param writer The Csv writer used for writing ETF data to the CSV file
     * @param holdings An array list of Holding objects
     * @param headers An array of headers that correspond to Holding properties
     */
    private static void AppendTopTenHoldings(ICsvBeanWriter writer, ArrayList<Holding> holdings, String[] headers){
        try {
            for (Holding holding : holdings) {
                writer.write(holding, headers);
                }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Appends Country Weight data to an existing CSV file
     * @param writer The Csv writer used for writing ETF data to the CSV file
     * @param countryWeights An array list of Country Weight objects
     * @param headers An array of headers that correspond to CountryWeight properties
     */
    private static void AppendCountryWeights(ICsvBeanWriter writer, ArrayList<CountryWeight> countryWeights, String[] headers){
        try {
            for (CountryWeight countryWeight :  countryWeights) {
                writer.write(countryWeight, headers);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Appends Sector Weight data to an existing CSV file
     * @param writer The Csv writer used for writing ETF data to the CSV file
     * @param sectorWeights An array list of Sector Weight objects
     * @param headers An array of headers that correspond to SectorWeight properties
     */
    private static void AppendSectorWeights(ICsvBeanWriter writer, ArrayList<SectorWeight> sectorWeights, String[] headers){
        try {
            for (SectorWeight sectorWeight : sectorWeights) {
                writer.write(sectorWeight, headers);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
