//,temp,ConsoleProxyDaoImpl.java,223,246,temp,StoragePoolHostDaoImpl.java,124,142
//,3
public class xxx {
    @Override
    public List<Pair<Long, Integer>> getDatacenterStoragePoolHostInfo(long dcId, boolean sharedOnly) {
        ArrayList<Pair<Long, Integer>> l = new ArrayList<Pair<Long, Integer>>();
        String sql = sharedOnly ? SHARED_STORAGE_POOL_HOST_INFO : STORAGE_POOL_HOST_INFO;
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        try {
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setLong(1, dcId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                l.add(new Pair<Long, Integer>(rs.getLong(1), rs.getInt(2)));
            }
        } catch (SQLException e) {
            s_logger.debug("SQLException: ", e);
        }
        return l;
    }

};