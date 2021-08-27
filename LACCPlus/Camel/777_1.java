//,temp,JdbcProducer.java,262,272,temp,MllpServerResource.java,903,913
//,3
public class xxx {
    private void closeQuietly(Statement stmt) {
        if (stmt != null) {
            try {
                if (!stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Throwable sqle) {
                LOG.debug("Error by closing statement", sqle);
            }
        }
    }

};