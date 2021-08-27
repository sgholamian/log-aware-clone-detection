//,temp,GrpcProducerAsyncTest.java,251,273,temp,GrpcProducerAsyncTest.java,227,249
//,2
public class xxx {
        @Override
        public StreamObserver<PingRequest> pingAsyncSync(StreamObserver<PongResponse> responseObserver) {
            StreamObserver<PingRequest> requestObserver = new StreamObserver<PingRequest>() {

                @Override
                public void onNext(PingRequest request) {
                    PongResponse response = PongResponse.newBuilder().setPongName(request.getPingName() + GRPC_TEST_PONG_VALUE)
                            .setPongId(request.getPingId()).build();
                    responseObserver.onNext(response);
                }

                @Override
                public void onError(Throwable t) {
                    LOG.info("Error in pingAsyncSync() " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
            return requestObserver;
        }

};