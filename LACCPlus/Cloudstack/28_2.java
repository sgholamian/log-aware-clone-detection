//,temp,ConfigurationServerImpl.java,808,819,temp,DatabaseConfig.java,1304,1338
//,3
public class xxx {
    @DB
    protected void saveRootDomain() {
        String insertSql = "insert into `cloud`.`domain` (id, name, parent, owner, path, level) values (1, 'ROOT', NULL, 2, '/', 0)";
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        try {
            PreparedStatement stmt = txn.prepareAutoCloseStatement(insertSql);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            s_logger.error("error creating ROOT domain", ex);
        }

        /*
        String updateSql = "update account set domain_id = 1 where id = 2";
        Transaction txn = Transaction.currentTxn();
        try {
            PreparedStatement stmt = txn.prepareStatement(updateSql);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            s_logger.error("error updating admin user", ex);
        } finally {
            txn.close();
        }

        updateSql = "update account set domain_id = 1 where id = 1";
        Transaction txn = Transaction.currentTxn();
        try {
            PreparedStatement stmt = txn.prepareStatement(updateSql);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            s_logger.error("error updating system user", ex);
        } finally {
            txn.close();
        }
         */
    }

};