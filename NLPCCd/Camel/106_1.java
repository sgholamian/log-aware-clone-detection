//,temp,sample_1007.java,2,14,temp,sample_6537.java,2,13
//,3
public class xxx {
private void closeQuietly(Statement stmt) {
if (stmt != null) {
try {
if (!stmt.isClosed()) {
stmt.close();
}
} catch (Throwable sqle) {


log.info("error by closing statement");
}
}
}

};