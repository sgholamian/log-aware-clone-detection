//,temp,sample_3521.java,2,15,temp,sample_1768.java,2,15
//,2
public class xxx {
protected void stop() throws Exception {
if (LOG.isInfoEnabled()) {
}
keepRunning = false;
if (cursor != null) {
cursor.close();
}
awaitStopped();
if (LOG.isInfoEnabled()) {


log.info("stopped mongodb tailable cursor consumer bound to collection db col");
}
}

};