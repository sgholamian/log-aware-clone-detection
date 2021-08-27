//,temp,GrpcProducerSecurityTest.java,170,178,temp,GrpcProducerSyncTest.java,139,147
//,2
public class xxx {
        @Override
        public void pingSyncSync(PingRequest request, StreamObserver<PongResponse> responseObserver) {
            LOG.info("gRPC server received data from PingPong service PingId={} PingName={}", request.getPingId(),
                    request.getPingName());
            PongResponse response = PongResponse.newBuilder().setPongName(request.getPingName() + GRPC_TEST_PONG_VALUE)
                    .setPongId(request.getPingId()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

};