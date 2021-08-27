//,temp,JdbcProducer.java,284,294,temp,JdbcProducer.java,250,260
//,2
public class xxx {
    private void closeQuietly(Connection con) {
        if (con != null) {
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (Throwable sqle) {
                LOG.debug("Error by closing connection", sqle);
            }
        }
    }

};