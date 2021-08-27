//,temp,sample_5784.java,2,13,temp,sample_3538.java,2,14
//,3
public class xxx {
public void prepareShutdown(boolean suspendOnly, boolean forced) {
if (suspendOnly) {
return;
}
shutdownPending = true;
forceShutdown = forced;
if (latch != null) {


log.info("preparing to shutdown waiting for consumer threads to complete");
}
}

};