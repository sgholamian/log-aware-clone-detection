//,temp,HttpComponent.java,920,930,temp,GrpcProducerStreamingTest.java,55,62
//,3
public class xxx {
    @AfterEach
    public void stopGrpcServer() throws IOException {
        if (grpcServer != null) {
            grpcServer.shutdown();
            LOG.info("gRPC server stopped");
            pingPongServer = null;
        }
    }

};