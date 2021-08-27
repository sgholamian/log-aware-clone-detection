//,temp,DefaultJDBCAdapter.java,143,169,temp,DefaultJDBCAdapter.java,109,127
//,3
public class xxx {
    @Override
    public void doDropTables(TransactionContext c) throws SQLException, IOException {
        Statement s = null;
        try {
            s = c.getConnection().createStatement();
            String[] dropStatments = this.statements.getDropSchemaStatements();
            for (int i = 0; i < dropStatments.length; i++) {
                // This will fail usually since the tables will be
                // created already.
                try {
                    LOG.debug("Executing SQL: " + dropStatments[i]);
                    s.execute(dropStatments[i]);
                } catch (SQLException e) {
                    LOG.warn("Could not drop JDBC tables; they may not exist." + " Failure was: " + dropStatments[i]
                            + " Message: " + e.getMessage() + " SQLState: " + e.getSQLState() + " Vendor code: "
                            + e.getErrorCode());
                    JDBCPersistenceAdapter.log("Failure details: ", e);
                }
            }
            commitIfAutoCommitIsDisabled(c);
        } finally {
            try {
                s.close();
            } catch (Throwable e) {
            }
        }
    }

};