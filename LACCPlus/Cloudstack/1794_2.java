//,temp,Ovm3StorageProcessor.java,681,694,temp,OvmResourceBase.java,811,819
//,3
public class xxx {
    public Answer execute(DestroyCommand cmd) {
        try {
            OvmVolume.destroy(_conn, cmd.getVolume().getPoolUuid(), cmd.getVolume().getPath());
            return new Answer(cmd, true, "Success");
        } catch (Exception e) {
            s_logger.debug("Destroy volume " + cmd.getVolume().getName() + " failed", e);
            return new Answer(cmd, false, e.getMessage());
        }
    }

};