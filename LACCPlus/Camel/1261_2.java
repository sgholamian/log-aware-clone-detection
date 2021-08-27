//,temp,GrpcProducerSecurityTest.java,110,122,temp,GrpcProducerSyncTest.java,68,86
//,3
public class xxx {
    @Test
    public void testPingSyncSyncMethodInvocation() throws Exception {
        LOG.info("gRPC PingSyncSync method test start");
        // Testing simple sync method invoke with host and port parameters
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        Object pongResponse = template.requestBody("direct:grpc-sync-sync", pingRequest);
        assertNotNull(pongResponse);
        assertTrue(pongResponse instanceof PongResponse);
        assertEquals(GRPC_TEST_PING_ID, ((PongResponse) pongResponse).getPongId());
        assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, ((PongResponse) pongResponse).getPongName());

        // Testing simple sync method with name described in .proto file instead
        // of generated class
        pongResponse = template.requestBody("direct:grpc-sync-proto-method-name", pingRequest);
        assertNotNull(pongResponse);
        assertTrue(pongResponse instanceof PongResponse);
        assertEquals(GRPC_TEST_PING_ID, ((PongResponse) pongResponse).getPongId());
    }

};