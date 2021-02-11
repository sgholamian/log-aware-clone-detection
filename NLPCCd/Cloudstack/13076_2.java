//,temp,sample_4341.java,2,10,temp,sample_4339.java,2,10
//,3
public class xxx {
public PreparedStatement prepareStatement(final String sql) throws SQLException {
final Connection conn = getConnection();
final PreparedStatement pstmt = conn.prepareStatement(sql);
if (s_stmtLogger.isTraceEnabled()) {


log.info("preparing");
}
}

};