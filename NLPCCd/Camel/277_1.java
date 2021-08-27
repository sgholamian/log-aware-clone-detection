//,temp,sample_2505.java,2,11,temp,sample_4377.java,2,9
//,3
public class xxx {
protected void doStop() throws Exception {
if (this.transport != null && this.transport.isListening()) {
if (LOG.isDebugEnabled()) {
}
this.transport.close();


log.info("stopped trap consumer on");
}
}

};