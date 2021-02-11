//,temp,VolumeDataStoreDaoImpl.java,328,344,temp,UsagePortForwardingRuleDaoImpl.java,53,71
//,3
public class xxx {
    @Override
    public void removeBy(long accountId, long pfId) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        PreparedStatement pstmt = null;
        try {
            txn.start();
            String sql = REMOVE_BY_USERID_PFID;
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setLong(1, accountId);
            pstmt.setLong(2, pfId);
            pstmt.executeUpdate();
            txn.commit();
        } catch (Exception e) {
            txn.rollback();
            s_logger.warn("Error removing UsagePortForwardingRuleVO", e);
        } finally {
            txn.close();
        }
    }

};