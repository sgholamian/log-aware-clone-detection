//,temp,AbstractJDBCLocker.java,93,101,temp,SocketProxy.java,202,208
//,3
public class xxx {
    protected void close(Statement statement) {
        if (null != statement) {
            try {
                statement.close();
            } catch (SQLException e1) {
                LOG.debug("exception while closing statement: " + e1, e1);
            }
        }
    }

};