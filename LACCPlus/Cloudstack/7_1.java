//,temp,OutOfBandManagementDaoImpl.java,98,108,temp,TemplateDataStoreDaoImpl.java,536,552
//,3
public class xxx {
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

};