//,temp,ApplicationMasterLauncher.java,97,106,temp,BlockManager.java,2748,2760
//,3
public class xxx {
  private void stopReplicationInitializer() {
    if (replicationQueuesInitializer != null) {
      replicationQueuesInitializer.interrupt();
      try {
        replicationQueuesInitializer.join();
      } catch (final InterruptedException e) {
        LOG.warn("Interrupted while waiting for replicationQueueInitializer. Returning..");
        return;
      } finally {
        replicationQueuesInitializer = null;
      }
    }
  }

};