//,temp,sample_8077.java,2,8,temp,sample_8078.java,2,8
//,2
public class xxx {
public static void startGrpcServer() throws Exception {
grpcServerWithTLS = NettyServerBuilder.forPort(GRPC_TLS_TEST_PORT) .sslContext(GrpcSslContexts.forServer(new File("src/test/resources/certs/server.pem"), new File("src/test/resources/certs/server.key")) .trustManager(new File("src/test/resources/certs/ca.pem")) .clientAuth(ClientAuth.REQUIRE) .sslProvider(SslProvider.OPENSSL) .build()) .addService(new PingPongImpl()).build().start();
grpcServerWithJWT = NettyServerBuilder.forPort(GRPC_JWT_TEST_PORT) .addService(new PingPongImpl()) .intercept(new JwtServerInterceptor(JwtAlgorithm.HMAC256, GRPC_JWT_CORRECT_SECRET, null, null)) .build() .start();


log.info("grpc server with the jwt auth started on port");
}

};