//,temp,Ovm3StorageProcessor.java,834,839,temp,Ovm3StorageProcessor.java,403,409
//,3
public class xxx {
    @Override
    public AttachAnswer attachIso(AttachCommand cmd) {
        LOGGER.debug("execute attachIso: "+ cmd.getClass());
        String vmName = cmd.getVmName();
        DiskTO disk = cmd.getDisk();
        return attachDetach(cmd, vmName, disk, true);
    }

};