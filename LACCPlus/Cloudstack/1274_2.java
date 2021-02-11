//,temp,DbUtil.java,234,257,temp,DbUtil.java,198,228
//,3
public class xxx {
    public static boolean getGlobalLock(String name, int timeoutSeconds) {
        Connection conn = getConnectionForGlobalLocks(name, true);
        if (conn == null) {
            s_logger.error("Unable to acquire DB connection for global lock system");
            return false;
        }

        try (PreparedStatement pstmt = conn.prepareStatement("SELECT COALESCE(GET_LOCK(?, ?),0)");) {
            pstmt.setString(1, name);
            pstmt.setInt(2, timeoutSeconds);

            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs != null && rs.first()) {
                    if (rs.getInt(1) > 0) {
                        return true;
                    } else {
                        if (s_logger.isDebugEnabled())
                            s_logger.debug("GET_LOCK() timed out on lock : " + name);
                    }
                }
            }
        } catch (SQLException e) {
            s_logger.error("GET_LOCK() throws exception ", e);
        } catch (Throwable e) {
            s_logger.error("GET_LOCK() throws exception ", e);
        }

        removeConnectionForGlobalLocks(name);
        closeAutoCloseable(conn, "connection for global lock");
        return false;
    }

};