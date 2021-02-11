//,temp,Ovm3StorageProcessor.java,766,787,temp,Ovm3StorageProcessor.java,625,642
//,3
public class xxx {
    @Override
    public Answer deleteSnapshot(DeleteCommand cmd) {
        LOGGER.debug("execute deleteSnapshot: "+ cmd.getClass());
        DataTO data = cmd.getData();
        SnapshotObjectTO snap = (SnapshotObjectTO) data;
        String storeUrl = data.getDataStore().getUrl();
        String snapUuid = snap.getPath();
        try {
            // snapshots/accountid/volumeid
            String secPoolUuid = pool.setupSecondaryStorage(storeUrl);
            String filePath = config.getAgentSecStoragePath()
                    + "/" + secPoolUuid + "/"
                    + snapUuid + ".raw";
            StoragePlugin sp = new StoragePlugin(c);
            sp.storagePluginDestroy(secPoolUuid, filePath);
            LOGGER.debug("Snapshot deletion success: " + filePath);
            return new Answer(cmd, true, "Deleted Snapshot " + filePath);
        } catch (Ovm3ResourceException e) {
            LOGGER.info("Snapshot deletion failed: " + e.toString(), e);
            return new CreateObjectAnswer(e.toString());
        }
    }

};