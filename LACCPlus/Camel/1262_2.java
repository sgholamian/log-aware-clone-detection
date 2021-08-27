//,temp,GrpcProducerSecurityTest.java,124,140,temp,GrpcProducerSecurityTest.java,96,108
//,3
public class xxx {
    @Test
    public void testWithEnableTLS() throws Exception {
        LOG.info("gRPC PingSyncSync method test start with TLS enable");
        // Testing simple sync method invoke using TLS negotiation
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        Object pongResponse = template.requestBody("direct:grpc-tls", pingRequest);

        assertNotNull(pongResponse);
        assertTrue(pongResponse instanceof PongResponse);
        assertEquals(GRPC_TEST_PING_ID, ((PongResponse) pongResponse).getPongId());
        assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, ((PongResponse) pongResponse).getPongName());
    }

};