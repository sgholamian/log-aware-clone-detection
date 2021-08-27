//,temp,RouteControlledStreamObserverTest.java,100,116,temp,GrpcProducerAsyncTest.java,155,181
//,3
public class xxx {
    @Test
    public void testSyncSyncMethodInAsync() throws Exception {
        LOG.info("gRPC pingSyncSync method async test start");
        final CountDownLatch latch = new CountDownLatch(1);
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        PongResponseStreamObserver responseObserver = new PongResponseStreamObserver(latch);

        nonBlockingStub.pingSyncSync(pingRequest, responseObserver);
        latch.await(5, TimeUnit.SECONDS);

        PongResponse pongResponse = responseObserver.getPongResponse();

        assertNotNull(pongResponse);
        assertEquals(GRPC_TEST_PING_ID, pongResponse.getPongId());
        assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, pongResponse.getPongName());
    }

};