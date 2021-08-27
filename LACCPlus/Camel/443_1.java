//,temp,Jt400Component.java,101,109,temp,ThriftConsumerSyncTest.java,65,72
//,3
public class xxx {
    @Override
    protected void doShutdown() throws Exception {
        super.doShutdown();
        if (connectionPool != null) {
            LOG.info("Shutting down the default connection pool {} ...", connectionPool);
            connectionPool.close();
            connectionPool = null;
        }
    }

};