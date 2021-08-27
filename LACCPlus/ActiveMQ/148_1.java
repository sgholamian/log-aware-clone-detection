//,temp,JDBCPersistenceAdapter.java,595,610,temp,JDBCPersistenceAdapter.java,308,327
//,3
public class xxx {
    @Override
    public void deleteAllMessages() throws IOException {
        TransactionContext c = getTransactionContext();
        c.getExclusiveConnection();
        try {
            getAdapter().doDropTables(c);
            getAdapter().setUseExternalMessageReferences(isUseExternalMessageReferences());
            getAdapter().doCreateTables(c);
            LOG.info("Persistence store purged.");
        } catch (SQLException e) {
            JDBCPersistenceAdapter.log("JDBC Failure: ", e);
            throw IOExceptionSupport.create(e);
        } finally {
            c.close();
        }
    }

};