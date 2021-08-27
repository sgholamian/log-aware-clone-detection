//,temp,sample_4307.java,2,10,temp,sample_1061.java,2,10
//,2
public class xxx {
public void stopThriftClient() throws Exception {
if (transport != null) {
transport.close();
transport = null;


log.info("connection to the thrift server closed");
}
}

};