//,temp,Ovm3StorageProcessor.java,539,571,temp,XenServerStorageProcessor.java,848,880
//,3
public class xxx {
    @Override
    public Answer createVolume(CreateObjectCommand cmd) {
        LOGGER.debug("execute createVolume: "+ cmd.getClass());
        DataTO data = cmd.getData();
        VolumeObjectTO volume = (VolumeObjectTO) data;
        try {
            /*
             * public Boolean storagePluginCreate(String uuid, String ssuuid,
             * String host, String file, Integer size)
             */
            String poolUuid = data.getDataStore().getUuid();
            String storeUrl = data.getDataStore().getUrl();
            URI uri = new URI(storeUrl);
            String host = uri.getHost();
            String file = getVirtualDiskPath(volume.getUuid(), poolUuid);
            Long size = volume.getSize();
            StoragePlugin sp = new StoragePlugin(c);
            FileProperties fp = sp.storagePluginCreate(poolUuid, host, file,
                    size, false);
            if (!fp.getName().equals(file)) {
                return new CreateObjectAnswer("Filename mismatch: "
                        + fp.getName() + " != " + file);
            }
            VolumeObjectTO newVol = new VolumeObjectTO();
            newVol.setName(volume.getName());
            newVol.setSize(fp.getSize());
            newVol.setPath(volume.getUuid());
            return new CreateObjectAnswer(newVol);
        } catch (Ovm3ResourceException | URISyntaxException e) {
            LOGGER.info("Volume creation failed: " + e.toString(), e);
            return new CreateObjectAnswer(e.toString());
        }
    }

};