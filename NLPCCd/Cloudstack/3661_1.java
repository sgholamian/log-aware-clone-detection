//,temp,sample_4328.java,2,13,temp,sample_4330.java,2,13
//,2
public class xxx {
public static Connection getStandaloneUsageConnection() {
try {
Connection conn = s_usageDS.getConnection();
if (s_connLogger.isTraceEnabled()) {
}
return conn;
} catch (SQLException e) {


log.info("unexpected exception");
}
}

};