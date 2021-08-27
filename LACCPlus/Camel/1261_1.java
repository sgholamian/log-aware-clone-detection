//,temp,GrpcProducerSecurityTest.java,110,122,temp,GrpcProducerSyncTest.java,68,86
//,3
public class xxx {
    @Test
    public void testWithCorrectJWT() throws Exception {
        LOG.info("gRPC PingSyncSync method test start with correct JWT authentication");
        // Testing simple sync method invoke using correct JWT authentication
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        Object pongResponse = template.requestBody("direct:grpc-correct-jwt", pingRequest);

        assertNotNull(pongResponse);
        assertTrue(pongResponse instanceof PongResponse);
        assertEquals(GRPC_TEST_PING_ID, ((PongResponse) pongResponse).getPongId());
        assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, ((PongResponse) pongResponse).getPongName());
    }

};