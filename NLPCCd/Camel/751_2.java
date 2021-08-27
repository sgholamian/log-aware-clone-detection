//,temp,sample_2250.java,2,12,temp,sample_2633.java,2,10
//,3
public class xxx {
public static void stopThriftServer() throws IOException {
if (server != null) {
server.stop();
serverTransport.close();


log.info("thrift server stoped");
}
}

};