//,temp,ThriftProducerBaseTest.java,60,67,temp,ThriftThreadPoolServerTest.java,87,94
//,2
public class xxx {
    @AfterEach
    public void stopThriftServer() throws IOException {
        if (server != null) {
            server.stop();
            serverTransport.close();
            LOG.info("Thrift secured server stoped");
        }
    }

};