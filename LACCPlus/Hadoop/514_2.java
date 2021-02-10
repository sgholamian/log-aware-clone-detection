//,temp,TestAMRMClientAsync.java,456,469,temp,TestAMRMClientAsync.java,441,454
//,2
public class xxx {
    @Override
    public void onContainersCompleted(List<ContainerStatus> statuses) {
      completedContainers = statuses;
      // wait for containers to be taken before returning
      synchronized (completedContainers) {
        while (completedContainers != null) {
          try {
            completedContainers.wait();
          } catch (InterruptedException ex) {
            LOG.error("Interrupted during wait", ex);
          }
        }
      }
    }

};