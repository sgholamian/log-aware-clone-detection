//,temp,Ovm3StorageProcessor.java,834,839,temp,Ovm3StorageProcessor.java,518,524
//,3
public class xxx {
    @Override
    public AttachAnswer attachVolume(AttachCommand cmd) {
        LOGGER.debug("execute attachVolume: "+ cmd.getClass());
        String vmName = cmd.getVmName();
        DiskTO disk = cmd.getDisk();
        return attachDetach(cmd, vmName, disk, true);
    }

};