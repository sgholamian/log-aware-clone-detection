//,temp,sample_6279.java,2,10,temp,sample_5344.java,2,10
//,3
public class xxx {
public void stopThriftClient() throws Exception {
if (transport != null) {
transport.close();
transport = null;


log.info("connection to the thrift server closed");
}
}

};