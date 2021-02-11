//,temp,sample_8664.java,2,12,temp,sample_7041.java,2,13
//,3
public class xxx {
protected void serviceStop() throws Exception {
keepRunning = false;
heartbeatThread.interrupt();
try {
heartbeatThread.join();
} catch (InterruptedException ex) {


log.info("error joining with heartbeat thread");
}
}

};