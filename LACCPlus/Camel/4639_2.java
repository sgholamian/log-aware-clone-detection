//,temp,GrpcConsumerAggregationTest.java,133,152,temp,GrpcConsumerConcurrentTest.java,105,135
//,3
public class xxx {
            @Override
            public void run() {
                int instanceId = createId();
                final CountDownLatch latch = new CountDownLatch(1);
                ManagedChannel asyncRequestChannel = NettyChannelBuilder.forAddress("localhost", GRPC_HEADERS_TEST_PORT)
                        .userAgent(GRPC_USER_AGENT_PREFIX + instanceId)
                        .usePlaintext().build();
                PingPongGrpc.PingPongStub asyncNonBlockingStub = PingPongGrpc.newStub(asyncRequestChannel);

                PongResponseStreamObserver responseObserver = new PongResponseStreamObserver(latch);

                final PingRequest pingRequest
                        = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(instanceId).build();
                StreamObserver<PingRequest> requestObserver = asyncNonBlockingStub.pingAsyncAsync(responseObserver);
                requestObserver.onNext(pingRequest);
                requestObserver.onNext(pingRequest);
                requestObserver.onCompleted();
                try {
                    latch.await(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    LOG.debug("Interrupted while waiting for the response", e);
                }

                PongResponse pongResponse = responseObserver.getPongResponse();

                assertNotNull(pongResponse, "instanceId = " + instanceId);
                assertEquals(instanceId, pongResponse.getPongId());
                assertEquals(GRPC_USER_AGENT_PREFIX + instanceId, pongResponse.getPongName());

                asyncRequestChannel.shutdown().shutdownNow();
            }

};