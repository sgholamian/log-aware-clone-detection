//,temp,sample_914.java,2,14,temp,sample_7041.java,2,13
//,3
public class xxx {
public void teardown() throws Exception {
keepRunning = false;
if (failoverThread != null) {
failoverThread.interrupt();
try {
failoverThread.join();
} catch (InterruptedException ex) {


log.info("error joining with failover thread");
}
}
}

};