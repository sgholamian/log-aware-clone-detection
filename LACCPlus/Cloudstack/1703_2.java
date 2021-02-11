//,temp,XenServerStorageProcessor.java,1769,1781,temp,XenServerStorageProcessor.java,1755,1767
//,3
public class xxx {
    @Override
    public Answer introduceObject(final IntroduceObjectCmd cmd) {
        try {
            final Connection conn = hypervisorResource.getConnection();
            final DataStoreTO store = cmd.getDataTO().getDataStore();
            final SR poolSr = hypervisorResource.getStorageRepository(conn, store.getUuid());
            poolSr.scan(conn);
            return new IntroduceObjectAnswer(cmd.getDataTO());
        } catch (final Exception e) {
            s_logger.debug("Failed to introduce object", e);
            return new Answer(cmd, false, e.toString());
        }
    }

};