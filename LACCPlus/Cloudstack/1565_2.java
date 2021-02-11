//,temp,Ovm3StorageProcessor.java,834,839,temp,Ovm3StorageProcessor.java,528,534
//,3
public class xxx {
    @Override
    public AttachAnswer dettachVolume(DettachCommand cmd) {
        LOGGER.debug("execute dettachVolume: "+ cmd.getClass());
        String vmName = cmd.getVmName();
        DiskTO disk = cmd.getDisk();
        return attachDetach(cmd, vmName, disk, false);
    }

};