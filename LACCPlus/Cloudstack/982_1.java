//,temp,UsageJobDaoImpl.java,42,59,temp,UsageDaoImpl.java,279,296
//,3
public class xxx {
    @Override
    public long getLastJobSuccessDateMillis() {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        PreparedStatement pstmt = null;
        String sql = GET_LAST_JOB_SUCCESS_DATE_MILLIS;
        try {
            pstmt = txn.prepareAutoCloseStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (Exception ex) {
            s_logger.error("error getting last usage job success date", ex);
        } finally {
            txn.close();
        }
        return 0L;
    }

};