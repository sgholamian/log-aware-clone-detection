//,temp,UsageNetworkDaoImpl.java,87,102,temp,UsageDaoImpl.java,461,478
//,3
public class xxx {
    @Override
    public void removeOldUsageRecords(int days) {
        String sql = DELETE_ALL_BY_INTERVAL;
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        PreparedStatement pstmt = null;
        try {
            txn.start();
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setLong(1, days);
            pstmt.executeUpdate();
            txn.commit();
        } catch (Exception ex) {
            txn.rollback();
            s_logger.error("error removing old cloud_usage records for interval: " + days);
        } finally {
            txn.close();
        }
    }

};