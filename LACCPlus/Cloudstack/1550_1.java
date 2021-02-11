//,temp,TransactionLegacy.java,229,240,temp,TransactionLegacy.java,212,218
//,3
public class xxx {
    public static Connection getStandaloneUsageConnection() {
        try {
            Connection conn = s_usageDS.getConnection();
            if (s_connLogger.isTraceEnabled()) {
                s_connLogger.trace("Retrieving a standalone connection for usage: dbconn" + System.identityHashCode(conn));
            }
            return conn;
        } catch (SQLException e) {
            s_logger.warn("Unexpected exception: ", e);
            return null;
        }
    }

};