//,temp,RouteControlledStreamObserverTest.java,86,98,temp,GrpcConsumerAggregationTest.java,83,95
//,3
public class xxx {
    @Test
    public void testSyncAsyncMethodInSync() {
        LOG.info("gRPC pingSyncAsync method blocking test start");
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        Iterator<PongResponse> pongResponseIter = blockingStub.pingSyncAsync(pingRequest);
        while (pongResponseIter.hasNext()) {
            PongResponse pongResponse = pongResponseIter.next();
            assertNotNull(pongResponse);
            assertEquals(GRPC_TEST_PING_ID, pongResponse.getPongId());
            assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, pongResponse.getPongName());
        }
    }

};