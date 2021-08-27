//,temp,sample_5269.java,2,14,temp,sample_685.java,2,12
//,3
public class xxx {
protected void cleanupResources(Connection conn, PreparedStatement ps, ResultSet rs) {
try {
if (rs != null) {
rs.close();
}
} catch (SQLException e) {


log.info("caught exception during resultset cleanup");
}
}

};