//,temp,OutOfBandManagementDaoImpl.java,98,108,temp,HAConfigDaoImpl.java,136,146
//,2
public class xxx {
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

};