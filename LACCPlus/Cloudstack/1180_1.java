//,temp,StorageManagerImpl.java,1466,1491,temp,StorageManagerImpl.java,1442,1464
//,3
public class xxx {
    @Override
    @DB
    public PrimaryDataStoreInfo cancelPrimaryStorageForMaintenance(CancelPrimaryStorageMaintenanceCmd cmd) throws ResourceUnavailableException {
        Long primaryStorageId = cmd.getId();
        StoragePoolVO primaryStorage = null;

        primaryStorage = _storagePoolDao.findById(primaryStorageId);

        if (primaryStorage == null) {
            String msg = "Unable to obtain lock on the storage pool in cancelPrimaryStorageForMaintenance()";
            s_logger.error(msg);
            throw new InvalidParameterValueException(msg);
        }

        if (primaryStorage.getStatus().equals(StoragePoolStatus.Up) || primaryStorage.getStatus().equals(StoragePoolStatus.PrepareForMaintenance)) {
            throw new StorageUnavailableException("Primary storage with id " + primaryStorageId + " is not ready to complete migration, as the status is:" + primaryStorage.getStatus().toString(),
                    primaryStorageId);
        }

        DataStoreProvider provider = _dataStoreProviderMgr.getDataStoreProvider(primaryStorage.getStorageProviderName());
        DataStoreLifeCycle lifeCycle = provider.getDataStoreLifeCycle();
        DataStore store = _dataStoreMgr.getDataStore(primaryStorage.getId(), DataStoreRole.Primary);
        lifeCycle.cancelMaintain(store);

        return (PrimaryDataStoreInfo)_dataStoreMgr.getDataStore(primaryStorage.getId(), DataStoreRole.Primary);
    }

};