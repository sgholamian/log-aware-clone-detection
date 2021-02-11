//,temp,TransactionLegacy.java,530,539,temp,TransactionLegacy.java,509,518
//,3
public class xxx {
    public PreparedStatement prepareAutoCloseStatement(final String sql, final String[] columnNames) throws SQLException {
        final Connection conn = getConnection();
        final PreparedStatement pstmt = conn.prepareStatement(sql, columnNames);
        if (s_stmtLogger.isTraceEnabled()) {
            s_stmtLogger.trace("Preparing: " + sql);
        }
        closePreviousStatement();
        _stmt = pstmt;
        return pstmt;
    }

};