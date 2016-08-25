package Utils;

import Model.Entities.CountryWeight;
import Model.Entities.Holding;
import Model.Entities.SectorWeight;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by reclaimer on 8/22/16.
 */
public class CsvUtil {

    /**
     * Creates a CSV file of relevant ETF data.
     * A file is first created to allow for appending only data that exists.
     * @param holdings An array list of Holding objects
     * @param countryWeights An array list of Country Weight objects
     * @param sectorWeights An array list of Sector Weight objects
     */
    public static void createCsv(ArrayList<Holding> holdings, ArrayList<CountryWeight> countryWeights, ArrayList<SectorWeight> sectorWeights) {
        String fileName = "etf-csv.txt";
        new File(fileName);
        // If data exists add it to the CSV file
        if(holdings != null){AppendTopTenHoldings(fileName, holdings);}
        if(countryWeights != null){AppendCountryWeights(fileName, countryWeights);}
        if (sectorWeights != null){AppendSectorWeights(fileName, sectorWeights);}
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
