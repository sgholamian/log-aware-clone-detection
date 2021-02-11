//,temp,CitrixResourceBase.java,3738,3756,temp,XenServerStorageProcessor.java,1116,1134
//,3
public class xxx {
    protected boolean destroySnapshotOnPrimaryStorage(final Connection conn, final String lastSnapshotUuid) {
        try {
            final VDI snapshot = getVDIbyUuid(conn, lastSnapshotUuid);
            if (snapshot == null) {
                // since this is just used to cleanup leftover bad snapshots, no need to throw exception
                s_logger.warn("Could not destroy snapshot " + lastSnapshotUuid + " due to can not find it");
                return false;
            }
            snapshot.destroy(conn);
            return true;
        } catch (final XenAPIException e) {
            final String msg = "Destroying snapshot: " + lastSnapshotUuid + " failed due to " + e.toString();
            s_logger.error(msg, e);
        } catch (final Exception e) {
            final String msg = "Destroying snapshot: " + lastSnapshotUuid + " failed due to " + e.toString();
            s_logger.warn(msg, e);
        }
        return false;
    }

};