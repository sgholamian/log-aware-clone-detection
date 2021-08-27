//,temp,ThriftProducerZlibCompressionTest.java,83,90,temp,ThriftProducerSecurityTest.java,97,104
//,2
public class xxx {
    @AfterAll
    public static void stopThriftServer() throws IOException {
        if (server != null) {
            server.stop();
            serverTransport.close();
            LOG.info("Thrift secured server stoped");
        }
    }

};