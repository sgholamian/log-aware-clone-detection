//,temp,Ovm3StorageProcessor.java,528,534,temp,Ovm3StorageProcessor.java,518,524
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