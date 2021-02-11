//,temp,AncientDataMotionStrategy.java,99,117,temp,StorageSystemDataMotionStrategy.java,598,623
//,3
public class xxx {
    private boolean needCacheStorage(DataObject srcData, DataObject destData) {
        DataTO srcTO = srcData.getTO();
        DataStoreTO srcStoreTO = srcTO.getDataStore();
        DataTO destTO = destData.getTO();
        DataStoreTO destStoreTO = destTO.getDataStore();

        // both snapshot and volume are on primary datastore - no need for a cache storage as hypervisor will copy directly
        if (srcStoreTO instanceof PrimaryDataStoreTO && destStoreTO instanceof PrimaryDataStoreTO) {
            return false;
        }

        if (srcStoreTO instanceof NfsTO || srcStoreTO.getRole() == DataStoreRole.ImageCache) {
            return false;
        }

        if (destStoreTO instanceof NfsTO || destStoreTO.getRole() == DataStoreRole.ImageCache) {
            return false;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("needCacheStorage true; dest at " + destTO.getPath() + ", dest role " + destStoreTO.getRole().toString() + "; src at " +
                    srcTO.getPath() + ", src role " + srcStoreTO.getRole().toString());
        }

        return true;
    }

};