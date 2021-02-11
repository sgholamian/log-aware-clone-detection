//,temp,UsageEventDaoImpl.java,193,211,temp,UsageDaoImpl.java,262,277
//,3
public class xxx {
    @Override
    public Long getLastUserStatsId() {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        String sql = GET_LAST_USER_STATS;
        try {
            pstmt = txn.prepareAutoCloseStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Long.valueOf(rs.getLong(1));
            }
        } catch (Exception ex) {
            s_logger.error("error getting last user stats id", ex);
        }
        return null;
    }

};