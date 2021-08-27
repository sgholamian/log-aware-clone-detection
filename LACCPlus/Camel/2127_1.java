//,temp,ThriftProducerZlibCompressionTest.java,61,81,temp,ThriftProducerBaseTest.java,45,58
//,3
public class xxx {
    @BeforeAll
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void startThriftServer() throws Exception {
        processor = new Calculator.Processor(new CalculatorSyncServerImpl());

        serverTransport = new TServerSocket(
                new InetSocketAddress(InetAddress.getByName("localhost"), THRIFT_TEST_PORT), THRIFT_CLIENT_TIMEOUT);
        TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
        args.processor(processor);
        args.protocolFactory(new TBinaryProtocol.Factory());
        args.transportFactory(new TZlibTransport.Factory());
        server = new TThreadPoolServer(args);

        Runnable simple = new Runnable() {
            public void run() {
                LOG.info("Thrift server with zlib compression started on port: {}", THRIFT_TEST_PORT);
                server.serve();
            }
        };
        new Thread(simple).start();
    }

};