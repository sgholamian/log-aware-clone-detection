//,temp,SnapshotDaoImpl.java,182,199,temp,CapacityDaoImpl.java,1068,1087
//,3
public class xxx {
    @Override
    public long getLastSnapshot(long volumeId, DataStoreRole role) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        String sql = GET_LAST_SNAPSHOT;
        try {
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setLong(1, volumeId);
            pstmt.setString(2, role.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (Exception ex) {
            s_logger.error("error getting last snapshot", ex);
        }
        return 0;
    }

};