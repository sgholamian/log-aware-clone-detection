//,temp,Ovm3StorageProcessor.java,413,419,temp,Ovm3StorageProcessor.java,403,409
//,2
public class xxx {
    @Override
    public AttachAnswer attachIso(AttachCommand cmd) {
        LOGGER.debug("execute attachIso: "+ cmd.getClass());
        String vmName = cmd.getVmName();
        DiskTO disk = cmd.getDisk();
        return attachDetach(cmd, vmName, disk, true);
    }

};