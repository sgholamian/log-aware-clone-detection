//,temp,Ovm3StorageProcessor.java,766,787,temp,Ovm3StorageProcessor.java,625,642
//,3
public class xxx {
    @Override
    public Answer deleteVolume(DeleteCommand cmd) {
        LOGGER.debug("execute deleteVolume: "+ cmd.getClass());
        DataTO data = cmd.getData();
        VolumeObjectTO volume = (VolumeObjectTO) data;
        try {
            String poolUuid = data.getDataStore().getUuid();
            String uuid = volume.getUuid();
            String path = getVirtualDiskPath(uuid, poolUuid);
            StoragePlugin sp = new StoragePlugin(c);
            sp.storagePluginDestroy(poolUuid, path);
            LOGGER.debug("Volume deletion success: " + path);
        } catch (Ovm3ResourceException e) {
            LOGGER.info("Volume deletion failed: " + e.toString(), e);
            return new CreateObjectAnswer(e.toString());
        }
        return new Answer(cmd);
    }

};