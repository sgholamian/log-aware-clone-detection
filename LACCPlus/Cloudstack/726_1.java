//,temp,Ovm3StorageProcessor.java,518,524,temp,Ovm3StorageProcessor.java,403,409
//,2
public class xxx {
    @Override
    public AttachAnswer attachVolume(AttachCommand cmd) {
        LOGGER.debug("execute attachVolume: "+ cmd.getClass());
        String vmName = cmd.getVmName();
        DiskTO disk = cmd.getDisk();
        return attachDetach(cmd, vmName, disk, true);
    }

};