//,temp,DbUtil.java,234,257,temp,DbUtil.java,198,228
//,3
public class xxx {
    public static boolean releaseGlobalLock(String name) {
        try (Connection conn = getConnectionForGlobalLocks(name, false);) {
            if (conn == null) {
                s_logger.error("Unable to acquire DB connection for global lock system");
                assert (false);
                return false;
            }

            try (PreparedStatement pstmt = conn.prepareStatement("SELECT COALESCE(RELEASE_LOCK(?), 0)");) {
                pstmt.setString(1, name);
                try (ResultSet rs = pstmt.executeQuery();) {
                    if (rs != null && rs.first()) {
                        return rs.getInt(1) > 0;
                    }
                    s_logger.error("releaseGlobalLock:RELEASE_LOCK() returns unexpected result");
                }
            }
        } catch (SQLException e) {
            s_logger.error("RELEASE_LOCK() throws exception ", e);
        } catch (Throwable e) {
            s_logger.error("RELEASE_LOCK() throws exception ", e);
        }
        return false;
    }

};