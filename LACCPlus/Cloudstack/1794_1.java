//,temp,Ovm3StorageProcessor.java,681,694,temp,OvmResourceBase.java,811,819
//,3
public class xxx {
    public Answer execute(DestroyCommand cmd) {
        LOGGER.debug("execute: "+ cmd.getClass());
        VolumeTO vol = cmd.getVolume();
        String vmName = cmd.getVmName();
        try {
            StoragePlugin store = new StoragePlugin(c);
            store.storagePluginDestroy(vol.getPoolUuid(), vol.getPath());
            return new Answer(cmd, true, "Success");
        } catch (Ovm3ResourceException e) {
            LOGGER.debug("Destroy volume " + vol.getName() + " failed for "
                    + vmName + " ", e);
            return new Answer(cmd, false, e.getMessage());
        }
    }

};