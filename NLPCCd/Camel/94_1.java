//,temp,sample_6673.java,2,11,temp,sample_3578.java,2,10
//,3
public class xxx {
protected void doStop() throws Exception {
if (syncTransport != null) {
syncTransport.close();
syncTransport = null;
} else if (asyncTransport != null) {


log.info("terminating asynchronous transport the remote thrift server");
}
}

};