//,temp,GrpcProducerClientInterceptorTest.java,114,122,temp,GrpcProducerAsyncTest.java,214,225
//,3
public class xxx {
        @Override
        public void pingSyncAsync(PingRequest request, StreamObserver<PongResponse> responseObserver) {
            LOG.info("gRPC server received data from PingAsyncResponse service PingId={} PingName={}", request.getPingId(),
                    request.getPingName());
            PongResponse response01 = PongResponse.newBuilder().setPongName(request.getPingName() + GRPC_TEST_PONG_VALUE)
                    .setPongId(GRPC_TEST_PONG_ID01).build();
            PongResponse response02 = PongResponse.newBuilder().setPongName(request.getPingName() + GRPC_TEST_PONG_VALUE)
                    .setPongId(GRPC_TEST_PONG_ID02).build();
            responseObserver.onNext(response01);
            responseObserver.onNext(response02);
            responseObserver.onCompleted();
        }

};