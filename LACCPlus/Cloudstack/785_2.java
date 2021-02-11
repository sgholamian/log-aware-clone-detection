//,temp,TransactionLegacy.java,509,518,temp,TransactionLegacy.java,212,218
//,3
public class xxx {
    public static Connection getStandaloneConnectionWithException() throws SQLException {
        Connection conn = s_ds.getConnection();
        if (s_connLogger.isTraceEnabled()) {
            s_connLogger.trace("Retrieving a standalone connection: dbconn" + System.identityHashCode(conn));
        }
        return conn;
    }

};