//,temp,ConfigurationServerImpl.java,808,819,temp,HAConfigDaoImpl.java,136,146
//,3
public class xxx {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {

                    TransactionLegacy txn = TransactionLegacy.currentTxn();
                    try {
                        PreparedStatement stmt1 = txn.prepareAutoCloseStatement(insertSql1);
                        stmt1.executeUpdate();
                        s_logger.debug("secondary storage vm copy password inserted into database");
                    } catch (SQLException ex) {
                        s_logger.warn("Failed to insert secondary storage vm copy password", ex);
                    }
                }

};