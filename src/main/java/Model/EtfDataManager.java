package Model;

import Model.Entities.EtfData;

/**
 *
 */

public class EtfDataManager {

    private RemoteDataManager remoteDataManager;
    private LocalDataManager localDataManager;

    public EtfDataManager(){
        this.remoteDataManager = new RemoteDataManager();
        this.localDataManager = new LocalDataManager();
    }

    /**
     * Retrieves an EtfData object.
     * A check is made to determine the method of retrieval:
     * If the ETF data already exists, it is simply fetched from a database
     * otherwise is fetched remotely from a webpage and then stored locally in a database.
     * @param etfSymbol The ETF that data is being requested on
     * @return An EtdData object
     */
    public EtfData getEtfData(String etfSymbol){
        System.out.println(etfExistsLocally("Check: " + etfSymbol));
        if(!etfExistsLocally(etfSymbol)){
            EtfData etfObject = fetchRemotely(etfSymbol);
            addToLocalDb(etfObject);
        }
        return fetchFromLocalDb(etfSymbol);
    }

    /**
     * Check if the requested ETF exists locally in a database already
     * @param etfSymbol The ETF that is being checked
     * @return A boolean: True, it exists; False, it does not exist
     */
    private boolean etfExistsLocally(String etfSymbol){
        return localDataManager.checkForData(etfSymbol);
    }

    /**
     * Fetches ETF data from a local database
     * @param etfSymbol The ETF that data is being fetched on
     * @return An EtfData object
     */
    private EtfData fetchFromLocalDb(String etfSymbol){
        return localDataManager.getEtfData(etfSymbol);
    }

    /**
     * Fetches ETF data remotly from a webpage
     * @param etfSymbol The ETF that data is being fetched on
     * @return An EtfData object
     */
    private EtfData fetchRemotely(String etfSymbol){
        return remoteDataManager.getEtfObject(etfSymbol);
    }

    /**
     * Adds an EtfData object to a database
     * @param etfObject The ETF object being added to a database
     */
    private void addToLocalDb(EtfData etfObject){
        localDataManager.addEtfData(etfObject);
    }
}
