//,temp,SnapshotDataStoreDaoImpl.java,289,308,temp,SnapshotDataStoreDaoImpl.java,268,287
//,2
public class xxx {
    @Override
    public SnapshotDataStoreVO findLatestSnapshotForVolume(Long volumeId, DataStoreRole role) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try (
                PreparedStatement pstmt = txn.prepareStatement(findLatestSnapshot);
                ){
            pstmt.setString(1, role.toString());
            pstmt.setLong(2, volumeId);
            try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    long sid = rs.getLong(1);
                    long snid = rs.getLong(3);
                    return findByStoreSnapshot(role, sid, snid);
                }
            }
        } catch (SQLException e) {
            s_logger.debug("Failed to find latest snapshot for volume: " + volumeId + " due to: "  + e.toString());
        }
        return null;
    }

};