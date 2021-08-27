//,temp,GrpcConsumerExceptionTest.java,63,69,temp,GrpcConsumerAggregationTest.java,71,81
//,3
public class xxx {
    @Test
    public void testExceptionExpected() throws Exception {
        LOG.info("gRPC expected exception test start");
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        assertThrows(StatusRuntimeException.class, () -> blockingStub.pingSyncSync(pingRequest));
    }

};