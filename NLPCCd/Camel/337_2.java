//,temp,sample_5859.java,2,8,temp,sample_3943.java,2,7
//,3
public class xxx {
public static void startGrpcServer() throws Exception {
grpcServer = ServerBuilder.forPort(GRPC_TEST_PORT).addService(new PingPongImpl()).build().start();


log.info("grpc server started on port");
}

};