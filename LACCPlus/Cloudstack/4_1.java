//,temp,HAConfigDaoImpl.java,136,146,temp,TemplateDataStoreDaoImpl.java,536,552
//,3
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