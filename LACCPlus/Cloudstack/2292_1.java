//,temp,OutOfBandManagementDaoImpl.java,96,110,temp,HAConfigDaoImpl.java,136,146
//,3
public class xxx {
    private void executeExpireOwnershipSql(final String sql, final long resource) {
        Transaction.execute(new TransactionCallbackNoReturn() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                TransactionLegacy txn = TransactionLegacy.currentTxn();
                try (final PreparedStatement pstmt = txn.prepareAutoCloseStatement(sql);) {
                    pstmt.setLong(1, resource);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    txn.rollback();
                    LOG.warn("Failed to expire ownership for out-of-band management server id: " + resource);
                }
            }
        });
    }

};