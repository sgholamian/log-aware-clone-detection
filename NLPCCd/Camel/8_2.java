//,temp,sample_4508.java,2,12,temp,sample_1006.java,2,14
//,3
public class xxx {
private void closeQuietly(ResultSet rs) {
if (rs != null) {
try {
if (!rs.isClosed()) {
rs.close();
}
} catch (Throwable sqle) {


log.info("error by closing result set");
}
}
}

};