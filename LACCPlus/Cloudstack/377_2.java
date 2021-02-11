//,temp,VMTemplatePoolDaoImpl.java,166,186,temp,ConsoleProxyDaoImpl.java,223,246
//,3
public class xxx {
    @Override
    public List<Pair<Long, Integer>> getDatacenterStoragePoolHostInfo(long dcId, boolean countAllPoolTypes) {
        ArrayList<Pair<Long, Integer>> l = new ArrayList<Pair<Long, Integer>>();

        TransactionLegacy txn = TransactionLegacy.currentTxn();
        ;
        PreparedStatement pstmt = null;
        try {
            if (countAllPoolTypes) {
                pstmt = txn.prepareAutoCloseStatement(STORAGE_POOL_HOST_INFO);
            } else {
                pstmt = txn.prepareAutoCloseStatement(SHARED_STORAGE_POOL_HOST_INFO);
            }
            pstmt.setLong(1, dcId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                l.add(new Pair<Long, Integer>(rs.getLong(1), rs.getInt(2)));
            }
        } catch (SQLException e) {
            s_logger.debug("Caught SQLException: ", e);
        }
        return l;
    }

};