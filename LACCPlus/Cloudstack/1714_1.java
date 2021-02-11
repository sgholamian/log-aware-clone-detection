//,temp,UsageDaoImpl.java,245,260,temp,UserStatisticsDaoImpl.java,117,133
//,3
public class xxx {
    @Override
    public Long getLastAccountId() {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        String sql = GET_LAST_ACCOUNT;
        try {
            pstmt = txn.prepareAutoCloseStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Long.valueOf(rs.getLong(1));
            }
        } catch (Exception ex) {
            s_logger.error("error getting last account id", ex);
        }
        return null;
    }

};