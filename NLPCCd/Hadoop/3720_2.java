//,temp,sample_7756.java,2,13,temp,sample_8181.java,2,13
//,3
public class xxx {
protected void serviceStop() throws Exception {
if (deletionThread != null) {
deletionThread.interrupt();
try {
deletionThread.join();
} catch (InterruptedException e) {


log.info("interrupted while waiting for deletion thread to complete closing db now");
}
}
}

};