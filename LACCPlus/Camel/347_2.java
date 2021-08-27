//,temp,GrpcProducerStreamingTest.java,48,53,temp,GrpcProducerAsyncTest.java,56,60
//,3
public class xxx {
    @BeforeAll
    public static void startGrpcServer() throws Exception {
        grpcServer = ServerBuilder.forPort(GRPC_TEST_PORT).addService(new PingPongImpl()).build().start();
        LOG.info("gRPC server started on port {}", GRPC_TEST_PORT);
    }

};