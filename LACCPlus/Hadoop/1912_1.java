//,temp,CgroupsLCEResourcesHandler.java,333,357,temp,CGroupsHandlerImpl.java,366,393
//,3
public class xxx {
  @VisibleForTesting
  boolean deleteCgroup(String cgroupPath) {
    boolean deleted = false;

    if (LOG.isDebugEnabled()) {
      LOG.debug("deleteCgroup: " + cgroupPath);
    }
    long start = clock.getTime();
    do {
      try {
        deleted = checkAndDeleteCgroup(new File(cgroupPath));
        if (!deleted) {
          Thread.sleep(deleteCgroupDelay);
        }
      } catch (InterruptedException ex) {
        // NOP
      }
    } while (!deleted && (clock.getTime() - start) < deleteCgroupTimeout);

    if (!deleted) {
      LOG.warn("Unable to delete cgroup at: " + cgroupPath +
          ", tried to delete for " + deleteCgroupTimeout + "ms");
    }
    return deleted;
  }

};