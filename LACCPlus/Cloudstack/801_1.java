//,temp,Ovm3StorageProcessor.java,846,851,temp,Ovm3StorageProcessor.java,518,524
//,3
public class xxx {
    public Answer execute(DettachCommand cmd) {
        LOGGER.debug("execute: "+ cmd.getClass());
        String vmName = cmd.getVmName();
        DiskTO disk = cmd.getDisk();
        return attachDetach(cmd, vmName, disk, false);
    }

};