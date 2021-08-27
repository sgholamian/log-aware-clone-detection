//,temp,GrpcConsumerAggregationTest.java,154,173,temp,GrpcConsumerSecurityTest.java,100,124
//,3
public class xxx {
    @Test
    public void testAsyncAsyncMethodInAsync() throws Exception {
        LOG.info("gRPC pingAsyncAsync method aync test start");
        final CountDownLatch latch = new CountDownLatch(1);
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        PongResponseStreamObserver responseObserver = new PongResponseStreamObserver(latch);

        StreamObserver<PingRequest> requestObserver = asyncNonBlockingStub.pingAsyncAsync(responseObserver);
        requestObserver.onNext(pingRequest);
        requestObserver.onNext(pingRequest);
        requestObserver.onCompleted();
        latch.await(5, TimeUnit.SECONDS);

        PongResponse pongResponse = responseObserver.getPongResponse();

        assertNotNull(pongResponse);
        assertEquals(GRPC_TEST_PING_ID, pongResponse.getPongId());
        assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, pongResponse.getPongName());
    }

};