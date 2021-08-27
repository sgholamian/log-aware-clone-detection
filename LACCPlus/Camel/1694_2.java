//,temp,GrpcConsumerConcurrentTest.java,101,140,temp,GrpcConsumerConcurrentTest.java,61,99
//,3
public class xxx {
    @Test
    public void testAsyncWithConcurrentThreads() throws Exception {
        RunnableAssert ra = new RunnableAssert("foo") {

            @Override
            public void run() {
                final CountDownLatch latch = new CountDownLatch(1);
                ManagedChannel asyncRequestChannel
                        = NettyChannelBuilder.forAddress("localhost", GRPC_ASYNC_REQUEST_TEST_PORT).usePlaintext().build();
                PingPongGrpc.PingPongStub asyncNonBlockingStub = PingPongGrpc.newStub(asyncRequestChannel);

                PongResponseStreamObserver responseObserver = new PongResponseStreamObserver(latch);
                int instanceId = createId();

                final PingRequest pingRequest
                        = PingRequest.newBuilder().setPingName(GRPC_TEST_PING_VALUE).setPingId(instanceId).build();
                StreamObserver<PingRequest> requestObserver = asyncNonBlockingStub.pingAsyncAsync(responseObserver);
                requestObserver.onNext(pingRequest);
                requestObserver.onNext(pingRequest);
                requestObserver.onCompleted();
                try {
                    latch.await(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    LOG.debug("Unhandled exception (probably safe to ignore): {}", e.getMessage(), e);
                }

                PongResponse pongResponse = responseObserver.getPongResponse();

                assertNotNull(pongResponse, "instanceId = " + instanceId);
                assertEquals(instanceId, pongResponse.getPongId());
                assertEquals(GRPC_TEST_PING_VALUE + GRPC_TEST_PONG_VALUE, pongResponse.getPongName());

                asyncRequestChannel.shutdown().shutdownNow();
            }
        };

        new MultithreadingTester().add(ra).numThreads(CONCURRENT_THREAD_COUNT).numRoundsPerThread(ROUNDS_PER_THREAD_COUNT)
                .run();
    }

};