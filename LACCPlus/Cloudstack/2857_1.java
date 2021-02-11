//,temp,Ovm3StorageProcessor.java,528,534,temp,Ovm3StorageProcessor.java,403,409
//,2
public class xxx {
    @Override
    public AttachAnswer dettachVolume(DettachCommand cmd) {
        LOGGER.debug("execute dettachVolume: "+ cmd.getClass());
        String vmName = cmd.getVmName();
        DiskTO disk = cmd.getDisk();
        return attachDetach(cmd, vmName, disk, false);
    }

};