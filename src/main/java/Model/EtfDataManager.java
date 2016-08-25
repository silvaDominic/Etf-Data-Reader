package Model;

import Model.Entities.EtfData;

/**
 * Created by reclaimer on 8/24/16.
 */
public class EtfDataManager {

    private RemoteDataManager remoteDataManager;
    private LocalDataManager localDataManager;

    public EtfDataManager(){
        this.remoteDataManager = new RemoteDataManager();
        this.localDataManager = new LocalDataManager();
    }

    public EtfData getEtfData(String etfSymbol){
        if(!etfExistsLocally(etfSymbol)){
            EtfData etfObject = fetchRemotely(etfSymbol);
            addToLocalDb(etfObject);
        }
        return fetchFromLocalDb(etfSymbol);
    }

    private boolean etfExistsLocally(String etfSymbol){
        return localDataManager.checkForData(etfSymbol);
    }

    private EtfData fetchFromLocalDb(String etfSymbol){
        return localDataManager.getEtfData(etfSymbol);
    }

    private EtfData fetchRemotely(String etfSymbol){
        return remoteDataManager.getEtfObject(etfSymbol);
    }

    private void addToLocalDb(EtfData etfObject){
        localDataManager.addEtfData(etfObject);
    }
}
