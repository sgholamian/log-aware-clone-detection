//,temp,ThriftConsumerAsyncTest.java,74,81,temp,ThriftConsumerSecurityTest.java,79,86
//,2
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