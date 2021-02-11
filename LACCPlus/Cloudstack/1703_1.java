//,temp,XenServerStorageProcessor.java,1769,1781,temp,XenServerStorageProcessor.java,1755,1767
//,3
public class xxx {
    @Override
    public Answer forgetObject(final ForgetObjectCmd cmd) {
        try {
            final Connection conn = hypervisorResource.getConnection();
            final DataTO data = cmd.getDataTO();
            final VDI vdi = VDI.getByUuid(conn, data.getPath());
            vdi.forget(conn);
            return new IntroduceObjectAnswer(cmd.getDataTO());
        } catch (final Exception e) {
            s_logger.debug("Failed to forget object", e);
            return new Answer(cmd, false, e.toString());
        }
    }

};