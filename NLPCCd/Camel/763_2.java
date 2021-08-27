//,temp,sample_6279.java,2,10,temp,sample_5344.java,2,10
//,3
public class xxx {
public static void stopThriftServer() throws IOException {
if (server != null) {
server.stop();
serverTransport.close();


log.info("thrift server with zlib compression stoped");
}
}

};