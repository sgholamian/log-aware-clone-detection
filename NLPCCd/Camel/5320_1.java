//,temp,sample_4286.java,2,10,temp,sample_8080.java,2,12
//,3
public class xxx {
protected void doStop() throws Exception {
if (channel != null) {
forwarder.shutdown();
forwarder = null;


log.info("terminating channel to the remote grpc server");
}
}

};