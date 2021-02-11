//,temp,TestAMRMClientAsync.java,456,469,temp,TestAMRMClientAsync.java,441,454
//,2
public class xxx {
    @Override
    public void onContainersAllocated(List<Container> containers) {
      allocatedContainers = containers;
      // wait for containers to be taken before returning
      synchronized (allocatedContainers) {
        while (allocatedContainers != null) {
          try {
            allocatedContainers.wait();
          } catch (InterruptedException ex) {
            LOG.error("Interrupted during wait", ex);
          }
        }
      }
    }

};