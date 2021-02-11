//,temp,DatabaseAccessObject.java,76,84,temp,AutoCloseableUtil.java,24,34
//,3
public class xxx {
    protected static void closePreparedStatement(PreparedStatement pstmt, String errorMessage) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            s_logger.warn(errorMessage, e);
        }
    }

};