//,temp,sample_4736.java,2,14,temp,sample_622.java,2,11
//,3
public class xxx {
protected void serviceStop() throws Exception {
launcherHandlingThread.interrupt();
try {
launcherHandlingThread.join();
} catch (InterruptedException ie) {


log.info("interrupted during join");
}
}

};