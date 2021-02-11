//,temp,sample_4736.java,2,14,temp,sample_622.java,2,11
//,3
public class xxx {
public void serviceStop() throws Exception {
running = false;
if (reloadThread != null) {
reloadThread.interrupt();
try {
reloadThread.join(THREAD_JOIN_TIMEOUT_MS);
} catch (InterruptedException e) {


log.info("reloadthread fails to join");
}
}
}

};