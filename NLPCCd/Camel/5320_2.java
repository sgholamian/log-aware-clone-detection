//,temp,sample_4286.java,2,10,temp,sample_8080.java,2,12
//,3
public class xxx {
public static void stopGrpcServer() throws IOException {
if (grpcServerWithTLS != null) {
grpcServerWithTLS.shutdown();
}
if (grpcServerWithJWT != null) {
grpcServerWithJWT.shutdown();


log.info("grpc server with jwt stoped");
}
}

};