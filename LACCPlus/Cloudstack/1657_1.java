//,temp,TransactionLegacy.java,745,770,temp,TransactionLegacy.java,242,253
//,3
public class xxx {
    protected void closeConnection() {
        closePreviousStatement();

        if (_conn == null) {
            return;
        }

        if (_txn) {
            s_connLogger.trace("txn: Not closing DB connection because we're still in a transaction.");
            return;
        }

        try {
            // we should only close db connection when it is not user managed
            if (_dbId != CONNECTED_DB) {
                if (s_connLogger.isTraceEnabled()) {
                    s_connLogger.trace("Closing DB connection: dbconn" + System.identityHashCode(_conn));
                }
                _conn.close();
                _conn = null;
            }

        } catch (final SQLException e) {
            s_logger.warn("Unable to close connection", e);
        }
    }

};