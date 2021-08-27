//,temp,sample_3221.java,2,17,temp,sample_4943.java,2,12
//,3
public class xxx {
protected static void closeDbConn(Connection dbConn) {
try {
if (dbConn != null && !dbConn.isClosed()) {
dbConn.close();
}
} catch (SQLException e) {


log.info("failed to close db connection");
}
}

};