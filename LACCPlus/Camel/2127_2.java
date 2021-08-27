//,temp,ThriftProducerZlibCompressionTest.java,61,81,temp,ThriftProducerBaseTest.java,45,58
//,3
public class xxx {
    @BeforeAll
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void startThriftServer() throws Exception {
        processor = new Calculator.Processor(new CalculatorSyncServerImpl());
        serverTransport = new TNonblockingServerSocket(THRIFT_TEST_PORT);
        server = new THsHaServer(new Args(serverTransport).processor(processor));
        Runnable simple = new Runnable() {
            public void run() {
                LOG.info("Thrift server started on port: {}", THRIFT_TEST_PORT);
                server.serve();
            }
        };
        new Thread(simple).start();
    }

};