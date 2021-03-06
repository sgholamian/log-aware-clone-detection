//,temp,HAConfigDaoImpl.java,133,148,temp,ManagementServerHostDaoImpl.java,168,188
//,3
public class xxx {
    @Override
    public void expireServerOwnership(final long serverId) {
        Transaction.execute(new TransactionCallbackNoReturn() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                TransactionLegacy txn = TransactionLegacy.currentTxn();
                try (final PreparedStatement pstmt = txn.prepareAutoCloseStatement(EXPIRE_OWNERSHIP);) {
                    pstmt.setLong(1, serverId);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    txn.rollback();
                    LOG.warn("Failed to expire HA ownership of management server id: " + serverId);
                }
            }
        });
    }

};