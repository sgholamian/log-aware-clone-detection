//,temp,AncientDataMotionStrategy.java,99,117,temp,StorageSystemDataMotionStrategy.java,598,623
//,3
public class xxx {
    protected boolean needCacheStorage(DataObject srcData, DataObject destData) {
        DataTO srcTO = srcData.getTO();
        DataStoreTO srcStoreTO = srcTO.getDataStore();

        if (srcStoreTO instanceof NfsTO || srcStoreTO.getRole() == DataStoreRole.ImageCache) {
            return false;
        }
        DataTO destTO = destData.getTO();
        DataStoreTO destStoreTO = destTO.getDataStore();

        if (destStoreTO instanceof NfsTO || destStoreTO.getRole() == DataStoreRole.ImageCache) {
            return false;
        }
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("needCacheStorage true, dest at " + destTO.getPath() + " dest role " + destStoreTO.getRole().toString() + srcTO.getPath() + " src role " +
                srcStoreTO.getRole().toString());
        }
        return true;
    }

};