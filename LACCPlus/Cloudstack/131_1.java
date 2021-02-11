//,temp,UsageLoadBalancerPolicyDaoImpl.java,53,71,temp,CapacityDaoImpl.java,543,564
//,3
public class xxx {
    @Override
    public void removeBy(long accountId, long lbId) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        PreparedStatement pstmt = null;
        try {
            txn.start();
            String sql = REMOVE_BY_USERID_LBID;
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setLong(1, accountId);
            pstmt.setLong(2, lbId);
            pstmt.executeUpdate();
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.warn("Error removing UsageLoadBalancerPolicyVO", e);
        } finally {
            txn.close();
        }
    }

};