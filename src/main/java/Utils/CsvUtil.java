package Utils;

import Model.CountryWeight;
import Model.Holding;
import Model.SectorWeight;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by reclaimer on 8/22/16.
 */
public class CsvUtil {

    public static void createCsv(ArrayList<Holding> holdings, ArrayList<CountryWeight> countryWeights, ArrayList<SectorWeight> sectorWeights) {
        String fileName = "etf-csv.txt";
        new File(fileName);
        if(holdings != null){AppendTopTenHoldings(fileName, holdings);}
        if(countryWeights != null){AppendCountryWeights(fileName, countryWeights);}
        if (sectorWeights != null){AppendSectorWeights(fileName, sectorWeights);}
    }

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
