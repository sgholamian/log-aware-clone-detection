//,temp,ThriftConsumerZlibCompressionTest.java,56,67,temp,ThriftConsumerSyncTest.java,54,63
//,3
public class xxx {
    @BeforeEach
    public void startThriftZlibClient() throws IOException, TTransportException {
        if (transport == null) {
            LOG.info("Connecting to the Thrift server with zlib compression on port: {}", THRIFT_TEST_PORT);

            transport = new TSocket("localhost", THRIFT_TEST_PORT, THRIFT_CLIENT_TIMEOUT);
            protocol = new TBinaryProtocol(new TZlibTransport(transport));
            thriftClient = new Calculator.Client(protocol);
            transport.open();
            LOG.info("Connected to the Thrift server with zlib compression");
        }
    }

};