//,temp,TransactionLegacy.java,242,253,temp,TransactionLegacy.java,229,240
//,2
public class xxx {
    public static Connection getStandaloneSimulatorConnection() {
        try {
            Connection conn = s_simulatorDS.getConnection();
            if (s_connLogger.isTraceEnabled()) {
                s_connLogger.trace("Retrieving a standalone connection for simulator: dbconn" + System.identityHashCode(conn));
            }
            return conn;
        } catch (SQLException e) {
            s_logger.warn("Unexpected exception: ", e);
            return null;
        }
    }

};