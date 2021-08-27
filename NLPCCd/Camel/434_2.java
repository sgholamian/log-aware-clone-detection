//,temp,sample_1939.java,2,12,temp,sample_3164.java,2,10
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