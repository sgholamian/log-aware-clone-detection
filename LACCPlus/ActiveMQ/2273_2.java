//,temp,DefaultJDBCAdapter.java,143,169,temp,DefaultJDBCAdapter.java,109,127
//,3
public class xxx {
    private void executeStatement(TransactionContext transactionContext, String createStatement, boolean ignoreStatementExecutionFailure) throws IOException {
        Statement statement = null;
        try {
            LOG.debug("Executing SQL: " + createStatement);
            statement = transactionContext.getConnection().createStatement();
            statement.execute(createStatement);

            commitIfAutoCommitIsDisabled(transactionContext);
        } catch (SQLException e) {
            if (ignoreStatementExecutionFailure) {
                LOG.debug("Could not create JDBC tables; The message table already existed. " + String.format(FAILURE_MESSAGE, createStatement, e.getMessage(), e.getSQLState(), e.getErrorCode()));
            } else {
                LOG.warn("Could not create JDBC tables; they could already exist. " + String.format(FAILURE_MESSAGE, createStatement, e.getMessage(), e.getSQLState(), e.getErrorCode()));
                JDBCPersistenceAdapter.log("Failure details: ", e);
            }
        } finally {
            closeStatement(statement);
        }
    }

};