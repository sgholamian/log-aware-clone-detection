//,temp,GrpcProducerSecurityTest.java,124,140,temp,GrpcProducerSecurityTest.java,96,108
//,3
public class xxx {
    @Test
    public void testWithIncorrectJWT() throws Exception {
        LOG.info("gRPC PingSyncSync method test start with incorrect JWT authentication");
        // Testing simple sync method invoke using incorrect JWT authentication
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();

        try {
            template.requestBody("direct:grpc-incorrect-jwt", pingRequest);
        } catch (Exception e) {
            assertNotNull(e);
            assertTrue(e.getCause().getCause() instanceof StatusRuntimeException);
            assertEquals(
                    "UNAUTHENTICATED: The Token's Signature resulted invalid when verified using the Algorithm: HmacSHA256",
                    e.getCause().getCause().getMessage());
        }
    }

};