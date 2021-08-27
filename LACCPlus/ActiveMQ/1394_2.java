//,temp,AbstractJDBCLocker.java,93,101,temp,SocketProxy.java,202,208
//,3
public class xxx {
    private void closeConnection(Bridge c) {
        try {
            c.close();
        } catch (Exception e) {
            LOG.debug("exception on close of: " + c, e);
        }
    }

};