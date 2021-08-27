//,temp,GrpcConsumerAggregationTest.java,115,131,temp,GrpcConsumerAggregationTest.java,97,113
//,2
public class xxx {
    @Test
    public void testSyncAsyncMethodInAsync() throws Exception {
        LOG.info("gRPC pingSyncAsync method aync test start");
        final CountDownLatch latch = new CountDownLatch(1);
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        PongResponseStreamObserver responseObserver = new PongResponseStreamObserver(latch);

        nonBlockingStub.pingSyncAsync(pingRequest, responseObserver);
        latch.await(5, TimeUnit.SECONDS);

        PongResponse pongResponse = responseObserver.getPongResponse();

        assertNotNull(pongResponse);
        assertEquals(GRPC_TEST_PING_ID, pongResponse.getPongId());
        assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, pongResponse.getPongName());
    }

};