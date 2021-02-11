//,temp,CitrixResourceBase.java,5166,5172,temp,SnapshotServiceImpl.java,570,578
//,3
public class xxx {
    public void umountSnapshotDir(final Connection conn, final Long dcId) {
        try {
            callHostPlugin(conn, "vmopsSnapshot", "unmountSnapshotsDir", "dcId", dcId.toString());
        } catch (final Exception e) {
            s_logger.debug("Failed to umount snapshot dir", e);
        }
    }

};