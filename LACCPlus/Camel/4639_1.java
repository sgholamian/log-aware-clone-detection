//,temp,GrpcConsumerAggregationTest.java,133,152,temp,GrpcConsumerConcurrentTest.java,105,135
//,3
public class xxx {
    @Test
    public void testAsyncSyncMethodInAsync() throws Exception {
        LOG.info("gRPC pingAsyncSync method aync test start");
        final CountDownLatch latch = new CountDownLatch(1);
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        PongResponseStreamObserver responseObserver = new PongResponseStreamObserver(latch);

        StreamObserver<PingRequest> requestObserver = asyncNonBlockingStub.pingAsyncSync(responseObserver);
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