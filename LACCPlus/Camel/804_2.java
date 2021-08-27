//,temp,GrpcConsumerExceptionTest.java,63,69,temp,GrpcConsumerAggregationTest.java,71,81
//,3
public class xxx {
    @Test
    public void testSyncSyncMethodInSync() throws Exception {
        LOG.info("gRPC pingSyncSync method blocking test start");
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        PongResponse pongResponse = blockingStub.pingSyncSync(pingRequest);

        assertNotNull(pongResponse);
        assertEquals(GRPC_TEST_PING_ID, pongResponse.getPongId());
        assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, pongResponse.getPongName());
    }

};