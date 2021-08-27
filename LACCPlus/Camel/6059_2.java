//,temp,GrpcProducerSyncTest.java,102,115,temp,GrpcProducerAsyncTest.java,98,125
//,3
public class xxx {
    @Test
    public void testPingSyncAsyncMethodInvocation() throws Exception {
        LOG.info("gRPC PingSyncAsync method test start");
        final CountDownLatch latch = new CountDownLatch(1);
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();

        // Testing async service call
        template.asyncCallbackSendBody("direct:grpc-sync-async", pingRequest, new SynchronizationAdapter() {

            @Override
            public void onComplete(Exchange exchange) {
                asyncPongResponse = exchange.getMessage().getBody();
                latch.countDown();
            }
        });
        latch.await(1, TimeUnit.SECONDS);

        assertNotNull(asyncPongResponse);
        assertTrue(asyncPongResponse instanceof List);

        @SuppressWarnings("unchecked")
        List<PongResponse> asyncPongResponseList = (List<PongResponse>) asyncPongResponse;
        assertEquals(2, asyncPongResponseList.size());
        assertEquals(GRPC_TEST_PONG_ID01, asyncPongResponseList.get(0).getPongId());
        assertEquals(GRPC_TEST_PONG_ID02, asyncPongResponseList.get(1).getPongId());
        assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, asyncPongResponseList.get(0).getPongName());
    }

};