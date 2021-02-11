//,temp,XenServerStorageProcessor.java,1727,1753,temp,XenServerStorageProcessor.java,558,581
//,3
public class xxx {
    @Override
    public Answer deleteSnapshot(final DeleteCommand cmd) {
        final SnapshotObjectTO snapshot = (SnapshotObjectTO) cmd.getData();
        final DataStoreTO store = snapshot.getDataStore();
        if (store.getRole() == DataStoreRole.Primary) {
            final Connection conn = hypervisorResource.getConnection();
            final VDI snapshotVdi = getVDIbyUuid(conn, snapshot.getPath());
            if (snapshotVdi == null) {
                return new Answer(null);
            }
            String errMsg = null;
            try {
                deleteVDI(conn, snapshotVdi);
            } catch (final BadServerResponse e) {
                s_logger.debug("delete snapshot failed:" + e.toString());
                errMsg = e.toString();
            } catch (final XenAPIException e) {
                s_logger.debug("delete snapshot failed:" + e.toString());
                errMsg = e.toString();
            } catch (final XmlRpcException e) {
                s_logger.debug("delete snapshot failed:" + e.toString());
                errMsg = e.toString();
            }
            return new Answer(cmd, false, errMsg);
        }
        return new Answer(cmd, false, "unsupported storage type");
    }

};