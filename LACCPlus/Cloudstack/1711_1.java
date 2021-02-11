//,temp,UsageDaoImpl.java,79,98,temp,CapacityDaoImpl.java,543,564
//,3
public class xxx {
    @Override
    public void deleteRecordsForAccount(Long accountId) {
        String sql = ((accountId == null) ? DELETE_ALL : DELETE_ALL_BY_ACCOUNTID);
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        PreparedStatement pstmt = null;
        try {
            txn.start();
            pstmt = txn.prepareAutoCloseStatement(sql);
            if (accountId != null) {
                pstmt.setLong(1, accountId.longValue());
            }
            pstmt.executeUpdate();
            txn.commit();
        } catch (Exception ex) {
            txn.rollback();
            s_logger.error("error retrieving usage vm instances for account id: " + accountId, ex);
        } finally {
            txn.close();
        }
    }

};