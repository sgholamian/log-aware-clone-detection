//,temp,JDBCPersistenceAdapter.java,595,610,temp,JDBCPersistenceAdapter.java,308,327
//,3
public class xxx {
    @Override
    public void init() throws Exception {
        getAdapter().setUseExternalMessageReferences(isUseExternalMessageReferences());

        if (isCreateTablesOnStartup()) {
            TransactionContext transactionContext = getTransactionContext();
            transactionContext.getExclusiveConnection();
            transactionContext.begin();
            try {
                try {
                    getAdapter().doCreateTables(transactionContext);
                } catch (SQLException e) {
                    LOG.warn("Cannot create tables due to: " + e);
                    JDBCPersistenceAdapter.log("Failure Details: ", e);
                }
            } finally {
                transactionContext.commit();
            }
        }
    }

};