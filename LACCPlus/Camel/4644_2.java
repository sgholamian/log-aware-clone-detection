//,temp,GrpcConsumerAggregationTest.java,154,173,temp,GrpcConsumerSecurityTest.java,100,124
//,3
public class xxx {
    @Test
    public void testWithEnableTLS() throws Exception {
        LOG.info("gRPC pingAsyncSync method aync test with TLS enable start");

        final CountDownLatch latch = new CountDownLatch(1);
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        PongResponseStreamObserver responseObserver = new PongResponseStreamObserver(latch);

        StreamObserver<PingRequest> requestObserver = tlsAsyncStub.pingAsyncSync(responseObserver);
        requestObserver.onNext(pingRequest);
        latch.await(5, TimeUnit.SECONDS);

        MockEndpoint mockEndpoint = getMockEndpoint("mock:tls-enable");
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedHeaderValuesReceivedInAnyOrder(GrpcConstants.GRPC_EVENT_TYPE_HEADER,
                GrpcConstants.GRPC_EVENT_TYPE_ON_NEXT);
        mockEndpoint.expectedHeaderValuesReceivedInAnyOrder(GrpcConstants.GRPC_METHOD_NAME_HEADER, "pingAsyncSync");
        mockEndpoint.assertIsSatisfied();

        PongResponse pongResponse = responseObserver.getPongResponse();
        assertNotNull(pongResponse);
        assertEquals(GRPC_TEST_PING_ID, pongResponse.getPongId());
        assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, pongResponse.getPongName());
    }

};