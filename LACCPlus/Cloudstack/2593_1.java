//,temp,UsageDaoImpl.java,147,175,temp,UsageDaoImpl.java,105,145
//,3
public class xxx {
    @Override
    public void updateAccounts(List<AccountVO> accounts) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            txn.start();
            String sql = UPDATE_ACCOUNT;
            PreparedStatement pstmt = null;
            pstmt = txn.prepareAutoCloseStatement(sql); // in reality I just want CLOUD_USAGE dataSource connection
            for (AccountVO acct : accounts) {
                pstmt.setString(1, acct.getAccountName());

                Date removed = acct.getRemoved();
                if (removed == null) {
                    pstmt.setString(2, null);
                } else {
                    pstmt.setString(2, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), acct.getRemoved()));
                }

                pstmt.setLong(3, acct.getId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            txn.commit();
        } catch (Exception ex) {
            txn.rollback();
            s_logger.error("error updating account to cloud_usage db", ex);
            throw new CloudRuntimeException(ex.getMessage());
        }
    }

};