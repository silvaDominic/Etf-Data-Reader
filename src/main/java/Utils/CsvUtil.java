package Utils;

import Model.Entities.CountryWeight;
import Model.Entities.Holding;
import Model.Entities.SectorWeight;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;

//TODO Figure out how files are built and downloaded with a server side ref.
@Component
public class CsvUtil {

    //TODO Don't think I should be creating a local file
    //TODO I think I just need to pass the append methods the file; How should the writer object refer to a nameless file?
    /**
     * Creates a CSV file of relevant ETF data.
     * A file is first created to allow for appending only data that exists.
     * @param holdings An array list of Holding objects
     * @param countryWeights An array list of Country Weight objects
     * @param sectorWeights An array list of Sector Weight objects
     */
    public static File createCsv(ArrayList<Holding> holdings, ArrayList<CountryWeight> countryWeights, ArrayList<SectorWeight> sectorWeights) {
        String fileName = "etf-csv.txt";
        File csvFile = new File(fileName);
        // If data exists add it to the CSV file
        if(holdings != null){AppendTopTenHoldings(fileName, holdings);}
        if(countryWeights != null){AppendCountryWeights(fileName, countryWeights);}
        if (sectorWeights != null){AppendSectorWeights(fileName, sectorWeights);}
        return csvFile;
    }

    /**
     * Appends Top Ten Holding data to an existing CSV file
     * @param filename The existing csv file
     * @param holdings An array list of Holding objects
     */
    private static void AppendTopTenHoldings(String filename, ArrayList<Holding> holdings){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename, true), "utf-8"))) {
            for (Holding holding : holdings) {
                writer.write(holding.getCompany() + ", " + holding.getWeight() + ", " + holding.getSharesHeld() + "\n");
                }
            writer.write("\n");
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
