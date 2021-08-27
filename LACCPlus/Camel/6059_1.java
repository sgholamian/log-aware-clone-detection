//,temp,GrpcProducerSyncTest.java,102,115,temp,GrpcProducerAsyncTest.java,98,125
//,3
public class xxx {
    @Test
    @SuppressWarnings("unchecked")
    public void testPingSyncAsyncMethodInvocation() throws Exception {
        LOG.info("gRPC PingSyncAsync method test start");
        // Testing simple method with sync request and asyc response in synchronous invocation style
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        Object pongResponse = template.requestBody("direct:grpc-sync-async", pingRequest);
        assertNotNull(pongResponse);
        assertTrue(pongResponse instanceof List<?>);
        assertEquals(GRPC_TEST_PONG_ID01, ((List<PongResponse>) pongResponse).get(0).getPongId());
        assertEquals(GRPC_TEST_PONG_ID02, ((List<PongResponse>) pongResponse).get(1).getPongId());
        assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, ((List<PongResponse>) pongResponse).get(0).getPongName());
    }

};