//,temp,GrpcConsumerExceptionTest.java,71,81,temp,GrpcConsumerSecurityTest.java,152,168
//,3
public class xxx {
    @Test
    public void testExchangeExceptionHandling() throws Exception {
        LOG.info("gRPC exchange exception handling test start");
        final CountDownLatch latch = new CountDownLatch(1);
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        PongResponseStreamObserver responseObserver = new PongResponseStreamObserver(latch);

        nonBlockingStub.pingSyncSync(pingRequest, responseObserver);
        latch.await(5, TimeUnit.SECONDS);
    }

};