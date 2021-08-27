//,temp,AbstractJDBCLocker.java,83,91,temp,ActiveMQEndpointWorker.java,245,254
//,3
public class xxx {
    protected void close(Connection connection) {
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e1) {
                LOG.debug("exception while closing connection: " + e1, e1);
            }
        }
    }

};