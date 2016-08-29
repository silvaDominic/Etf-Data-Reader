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
     * @param holdings An array list of Holding objects
     * @param countryWeights An array list of Country Weight objects
     * @param sectorWeights An array list of Sector Weight objects
     */
/*    public static File createCsv(EtfData etfObject, HttpServletResponse response) {
        String fileName = etfObject.getSymbol() + ".csv";
        ArrayList<Holding> holdings = etfObject.getTopTenHoldings();
        ArrayList<CountryWeight> countryWeights = etfObject.getCountryWeights();
        ArrayList<SectorWeight> sectorWeights = etfObject.getSectorWeights();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        // If data exists add it to the CSV file
        if(holdings != null){AppendTopTenHoldings(csvWriter, fileName, holdings);}
        if(countryWeights != null){AppendCountryWeights(fileName, countryWeights);}
        if (sectorWeights != null){AppendSectorWeights(fileName, sectorWeights);}

        response.setContentType("text/csv");

        //Set headers
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\" ", fileName);
        response.setHeader(headerKey, headerValue);
    }*/

    //TODO Learn more about how writers work
    /**
     * Appends Top Ten Holding data to an existing CSV file
     * @param filename The existing csv file
     * @param holdings An array list of Holding objects
     */
    private static void AppendTopTenHoldings(ICsvBeanWriter writer, String filename, ArrayList<Holding> holdings){
        try {
            for (Holding holding : holdings) {
                writer.write(holding.getCompany() + ", " + holding.getWeight() + ", " + holding.getSharesHeld() + "\n");
                }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Appends Country Weight data to an existing CSV file
     * @param filename The existing csv file
     * @param countryWeights An array list of Country Weight objects
     */
    private static void AppendCountryWeights(String filename, ArrayList<CountryWeight> countryWeights){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename, true), "utf-8"))) {
            for (CountryWeight countryWeight :  countryWeights) {
                writer.write(countryWeight.getCountry() + ", " + countryWeight.getWeight() + "\n");
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Appends Sector Weight data to an existing CSV file
     * @param filename The existing csv file
     * @param sectorWeights An array list of Sector Weight objects
     */
    private static void AppendSectorWeights(String filename, ArrayList<SectorWeight> sectorWeights){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename, true), "utf-8"))) {
            for (SectorWeight sectorWeight : sectorWeights) {
                writer.write(sectorWeight.getSector() + ", " + sectorWeight.getWeight() + "\n");
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
