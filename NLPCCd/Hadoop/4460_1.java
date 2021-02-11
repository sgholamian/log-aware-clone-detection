//,temp,sample_3459.java,2,13,temp,sample_8216.java,2,16
//,3
public class xxx {
private void stopReplicationInitializer() {
if (replicationQueuesInitializer != null) {
replicationQueuesInitializer.interrupt();
try {
replicationQueuesInitializer.join();
} catch (final InterruptedException e) {


log.info("interrupted while waiting for replicationqueueinitializer returning");
}
}
}

};