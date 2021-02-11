//,temp,ObjectInDataStoreManagerImpl.java,243,289,temp,ObjectInDataStoreManagerImpl.java,195,241
//,3
public class xxx {
    @Override
    public boolean delete(DataObject dataObj) {
        long objId = dataObj.getId();
        DataStore dataStore = dataObj.getDataStore();
        if (dataStore.getRole() == DataStoreRole.Primary) {
            if (dataObj.getType() == DataObjectType.TEMPLATE) {
                VMTemplateStoragePoolVO destTmpltPool = templatePoolDao.findByPoolTemplate(dataStore.getId(), objId);
                if (destTmpltPool != null) {
                    return templatePoolDao.remove(destTmpltPool.getId());
                } else {
                    s_logger.warn("Template " + objId + " is not found on storage pool " + dataStore.getId() + ", so no need to delete");
                    return true;
                }
            }
        } else {
            // Image store
            switch (dataObj.getType()) {
                case TEMPLATE:
                    TemplateDataStoreVO destTmpltStore = templateDataStoreDao.findByStoreTemplate(dataStore.getId(), objId);
                    if (destTmpltStore != null) {
                        return templateDataStoreDao.remove(destTmpltStore.getId());
                    } else {
                        s_logger.warn("Template " + objId + " is not found on image store " + dataStore.getId() + ", so no need to delete");
                        return true;
                    }
                case SNAPSHOT:
                    SnapshotDataStoreVO destSnapshotStore = snapshotDataStoreDao.findByStoreSnapshot(dataStore.getRole(), dataStore.getId(), objId);
                    if (destSnapshotStore != null) {
                        return snapshotDataStoreDao.remove(destSnapshotStore.getId());
                    } else {
                        s_logger.warn("Snapshot " + objId + " is not found on image store " + dataStore.getId() + ", so no need to delete");
                        return true;
                    }
                case VOLUME:
                    VolumeDataStoreVO destVolumeStore = volumeDataStoreDao.findByStoreVolume(dataStore.getId(), objId);
                    if (destVolumeStore != null) {
                        return volumeDataStoreDao.remove(destVolumeStore.getId());
                    } else {
                        s_logger.warn("Volume " + objId + " is not found on image store " + dataStore.getId() + ", so no need to delete");
                        return true;
                    }
            }
        }

        s_logger.warn("Unsupported data object (" + dataObj.getType() + ", " + dataObj.getDataStore() + ")");
        return false;
    }

};