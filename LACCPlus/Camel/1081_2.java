//,temp,ThriftConsumerZlibCompressionTest.java,56,67,temp,ThriftConsumerSyncTest.java,54,63
//,3
public class xxx {
    @BeforeEach
    public void startThriftClient() throws IOException, TTransportException {
        if (transport == null) {
            LOG.info("Connecting to the Thrift server on port: {}", THRIFT_TEST_PORT);
            transport = new TSocket("localhost", THRIFT_TEST_PORT);
            transport.open();
            protocol = new TBinaryProtocol(new TFramedTransport(transport));
            thriftClient = (new Calculator.Client.Factory()).getClient(protocol);
        }
    }

};