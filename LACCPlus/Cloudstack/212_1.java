//,temp,UsageVmDiskDaoImpl.java,91,106,temp,UsageLoadBalancerPolicyDaoImpl.java,53,71
//,3
public class xxx {
    @Override
    public void deleteOldStats(long maxEventTime) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        String sql = DELETE_OLD_STATS;
        PreparedStatement pstmt = null;
        try {
            txn.start();
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setLong(1, maxEventTime);
            pstmt.executeUpdate();
            txn.commit();
        } catch (Exception ex) {
            txn.rollback();
            s_logger.error("error deleting old usage disk stats", ex);
        }
    }

};