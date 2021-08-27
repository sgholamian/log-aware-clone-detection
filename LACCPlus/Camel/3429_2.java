//,temp,GrpcProducerClientInterceptorTest.java,54,59,temp,GrpcProducerSyncTest.java,54,58
//,3
public class xxx {
    @BeforeAll
    public static void startGrpcServer() throws Exception {
        grpcServer = ServerBuilder.forPort(GRPC_TEST_PORT).addService(new PingPongImpl()).build().start();
        LOG.info("gRPC server started on port {}", GRPC_TEST_PORT);
    }

};