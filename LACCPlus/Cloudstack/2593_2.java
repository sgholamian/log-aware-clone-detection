//,temp,UsageDaoImpl.java,147,175,temp,UsageDaoImpl.java,105,145
//,3
public class xxx {
    @Override
    public void saveAccounts(List<AccountVO> accounts) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            txn.start();
            String sql = INSERT_ACCOUNT;
            PreparedStatement pstmt = null;
            pstmt = txn.prepareAutoCloseStatement(sql); // in reality I just want CLOUD_USAGE dataSource connection
            for (AccountVO acct : accounts) {
                pstmt.setLong(1, acct.getId());
                pstmt.setString(2, acct.getAccountName());
                pstmt.setShort(3, acct.getType());

                //prevent autoboxing NPE by defaulting to User role
                if(acct.getRoleId() == null){
                    pstmt.setLong(4, RoleType.User.getId());
                }else{
                    pstmt.setLong(4, acct.getRoleId());
                }

                pstmt.setLong(5, acct.getDomainId());

                Date removed = acct.getRemoved();
                if (removed == null) {
                    pstmt.setString(6, null);
                } else {
                    pstmt.setString(6, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), acct.getRemoved()));
                }

                pstmt.setBoolean(7, acct.getNeedsCleanup());

                pstmt.addBatch();
            }
            pstmt.executeBatch();
            txn.commit();
        } catch (Exception ex) {
            txn.rollback();
            s_logger.error("error saving account to cloud_usage db", ex);
            throw new CloudRuntimeException(ex.getMessage());
        }
    }

};