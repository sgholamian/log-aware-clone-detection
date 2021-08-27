//,temp,sample_5859.java,2,8,temp,sample_3943.java,2,7
//,3
public class xxx {
private void start() throws IOException {
int port = 8080;
server = ServerBuilder.forPort(port).addService(new HelloCamelImpl()).build().start();


log.info("server started i m listening on");
}

};