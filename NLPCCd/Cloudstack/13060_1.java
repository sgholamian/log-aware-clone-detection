//,temp,sample_4341.java,2,10,temp,sample_4340.java,2,10
//,3
public class xxx {
public PreparedStatement prepareAutoCloseStatement(final String sql, final String[] columnNames) throws SQLException {
final Connection conn = getConnection();
final PreparedStatement pstmt = conn.prepareStatement(sql, columnNames);
if (s_stmtLogger.isTraceEnabled()) {


log.info("preparing");
}
}

};