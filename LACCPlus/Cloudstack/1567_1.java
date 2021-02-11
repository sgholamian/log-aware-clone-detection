//,temp,Ovm3StorageProcessor.java,834,839,temp,Ovm3StorageProcessor.java,403,409
//,3
public class xxx {
    public Answer execute(AttachCommand cmd) {
        LOGGER.debug("execute: "+ cmd.getClass());
        String vmName = cmd.getVmName();
        DiskTO disk = cmd.getDisk();
        return attachDetach(cmd, vmName, disk, true);
    }

};