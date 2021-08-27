//,temp,sample_1009.java,2,14,temp,sample_2512.java,2,12
//,3
public class xxx {
private void closeQuietly(Connection con) {
if (con != null) {
try {
if (!con.isClosed()) {
con.close();
}
} catch (Throwable sqle) {


log.info("error by closing connection");
}
}
}

};