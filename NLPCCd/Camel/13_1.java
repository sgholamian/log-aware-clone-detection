//,temp,sample_5016.java,2,8,temp,sample_6795.java,2,7
//,3
public class xxx {
public void startGrpcServer() throws Exception {
pingPongServer = new PingPongImpl();
grpcServer = ServerBuilder.forPort(GRPC_TEST_PORT).addService(pingPongServer).build().start();


log.info("grpc server started on port");
}

};