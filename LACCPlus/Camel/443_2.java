//,temp,Jt400Component.java,101,109,temp,ThriftConsumerSyncTest.java,65,72
//,3
public class xxx {
    @AfterEach
    public void stopThriftClient() throws Exception {
        if (transport != null) {
            transport.close();
            transport = null;
            LOG.info("Connection to the Thrift server closed");
        }
    }

};