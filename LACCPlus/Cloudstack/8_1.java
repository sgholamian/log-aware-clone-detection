//,temp,TransactionLegacy.java,467,474,temp,TransactionLegacy.java,212,218
//,3
public class xxx {
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        final Connection conn = getConnection();
        final PreparedStatement pstmt = conn.prepareStatement(sql);
        if (s_stmtLogger.isTraceEnabled()) {
            s_stmtLogger.trace("Preparing: " + sql);
        }
        return pstmt;
    }

};