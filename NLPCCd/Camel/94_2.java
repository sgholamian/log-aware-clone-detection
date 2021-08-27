//,temp,sample_6673.java,2,11,temp,sample_3578.java,2,10
//,3
public class xxx {
public static void stopThriftServer() throws IOException {
if (server != null) {
server.stop();
serverTransport.close();


log.info("thrift secured server stoped");
}
}

};