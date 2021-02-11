//,temp,TransactionLegacy.java,509,518,temp,TransactionLegacy.java,487,496
//,3
public class xxx {
    public PreparedStatement prepareAutoCloseStatement(final String sql, final int autoGeneratedKeys) throws SQLException {
        final Connection conn = getConnection();
        final PreparedStatement pstmt = conn.prepareStatement(sql, autoGeneratedKeys);
        if (s_stmtLogger.isTraceEnabled()) {
            s_stmtLogger.trace("Preparing: " + sql);
        }
        closePreviousStatement();
        _stmt = pstmt;
        return pstmt;
    }

};