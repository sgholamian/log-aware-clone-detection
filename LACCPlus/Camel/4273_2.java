//,temp,GrpcConsumerExceptionTest.java,71,81,temp,GrpcConsumerSecurityTest.java,152,168
//,3
public class xxx {
    @Test
    public void testWithIncorrectJWT() throws Exception {
        LOG.info("gRPC pingAsyncSync method aync test with correct JWT start");

        final CountDownLatch latch = new CountDownLatch(1);
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        PongResponseStreamObserver responseObserver = new PongResponseStreamObserver(latch);

        StreamObserver<PingRequest> requestObserver = jwtIncorrectAsyncStub.pingAsyncSync(responseObserver);
        requestObserver.onNext(pingRequest);
        latch.await(5, TimeUnit.SECONDS);

        MockEndpoint mockEndpoint = getMockEndpoint("mock:jwt-incorrect-secret");
        mockEndpoint.expectedMessageCount(0);
        mockEndpoint.assertIsSatisfied();
    }

};