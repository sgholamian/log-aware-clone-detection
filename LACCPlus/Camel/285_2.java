//,temp,GrpcConsumerSecurityTest.java,126,150,temp,GrpcConsumerPropagationTest.java,69,93
//,2
public class xxx {
    @Test
    public void testOnNextPropagation() throws Exception {
        LOG.info("gRPC pingAsyncSync method aync test start");

        final CountDownLatch latch = new CountDownLatch(1);
        PingRequest pingRequest
                = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(GRPC_TEST_PING_ID).build();
        PongResponseStreamObserver responseObserver = new PongResponseStreamObserver(latch);

        StreamObserver<PingRequest> requestObserver = asyncOnNextStub.pingAsyncSync(responseObserver);
        requestObserver.onNext(pingRequest);
        latch.await(5, TimeUnit.SECONDS);

        MockEndpoint mockEndpoint = getMockEndpoint("mock:async-on-next-propagation");
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