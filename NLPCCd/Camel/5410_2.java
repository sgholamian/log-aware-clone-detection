//,temp,sample_7922.java,2,12,temp,sample_3240.java,2,10
//,3
public class xxx {
public void stopThriftServer() throws IOException {
if (server != null) {
server.stop();
serverTransport.close();


log.info("thrift secured server stoped");
}
}

};