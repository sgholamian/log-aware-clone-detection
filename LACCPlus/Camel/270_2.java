//,temp,GrpcProducerAsyncTest.java,127,153,temp,GrpcProducerAsyncTest.java,70,96
//,2
public class xxx {
    @Test
    public void testPingSyncSyncMethodInvocation() throws Exception {
        LOG.info("gRPC PingSyncSync method test start");
        final CountDownLatch latch = new CountDownLatch(1);
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();

        // Testing sync service call with async style invocation
        template.asyncCallbackSendBody("direct:grpc-sync-sync", pingRequest, new SynchronizationAdapter() {

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
        assertEquals(1, asyncPongResponseList.size());
        assertEquals(GRPC_TEST_PING_ID, asyncPongResponseList.get(0).getPongId());
        assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, asyncPongResponseList.get(0).getPongName());
    }

};