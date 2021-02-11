//,temp,Ovm3StorageProcessor.java,518,524,temp,Ovm3StorageProcessor.java,413,419
//,2
public class xxx {
    @Override
    public AttachAnswer dettachIso(DettachCommand cmd) {
        LOGGER.debug("execute dettachIso: "+ cmd.getClass());
        String vmName = cmd.getVmName();
        DiskTO disk = cmd.getDisk();
        return attachDetach(cmd, vmName, disk, false);
    }

};