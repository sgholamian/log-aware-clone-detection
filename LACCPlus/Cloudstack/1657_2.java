//,temp,TransactionLegacy.java,745,770,temp,TransactionLegacy.java,242,253
//,3
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