//,temp,StorageManagerImpl.java,1466,1491,temp,StorageManagerImpl.java,1442,1464
//,3
public class xxx {
    @Override
    @DB
    public PrimaryDataStoreInfo preparePrimaryStorageForMaintenance(Long primaryStorageId) throws ResourceUnavailableException, InsufficientCapacityException {
        StoragePoolVO primaryStorage = null;
        primaryStorage = _storagePoolDao.findById(primaryStorageId);

        if (primaryStorage == null) {
            String msg = "Unable to obtain lock on the storage pool record in preparePrimaryStorageForMaintenance()";
            s_logger.error(msg);
            throw new InvalidParameterValueException(msg);
        }

        if (!primaryStorage.getStatus().equals(StoragePoolStatus.Up) && !primaryStorage.getStatus().equals(StoragePoolStatus.ErrorInMaintenance)) {
            throw new InvalidParameterValueException("Primary storage with id " + primaryStorageId + " is not ready for migration, as the status is:" + primaryStorage.getStatus().toString());
        }

        DataStoreProvider provider = _dataStoreProviderMgr.getDataStoreProvider(primaryStorage.getStorageProviderName());
        DataStoreLifeCycle lifeCycle = provider.getDataStoreLifeCycle();
        DataStore store = _dataStoreMgr.getDataStore(primaryStorage.getId(), DataStoreRole.Primary);
        lifeCycle.maintain(store);

        return (PrimaryDataStoreInfo)_dataStoreMgr.getDataStore(primaryStorage.getId(), DataStoreRole.Primary);
    }

};