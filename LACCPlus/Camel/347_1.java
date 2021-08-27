//,temp,GrpcProducerStreamingTest.java,48,53,temp,GrpcProducerAsyncTest.java,56,60
//,3
public class xxx {
    @BeforeEach
    public void startGrpcServer() throws Exception {
        pingPongServer = new PingPongImpl();
        grpcServer = ServerBuilder.forPort(GRPC_TEST_PORT).addService(pingPongServer).build().start();
        LOG.info("gRPC server started on port {}", GRPC_TEST_PORT);
    }

};