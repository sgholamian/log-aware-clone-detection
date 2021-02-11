//,temp,PendingReplicationBlocks.java,232,253,temp,NMContainerTokenSecretManager.java,214,234
//,3
public class xxx {
  protected synchronized void removeAnyContainerTokenIfExpired() {
    // Trying to remove any container if its container token has expired.
    Iterator<Entry<Long, List<ContainerId>>> containersI =
        this.recentlyStartedContainerTracker.entrySet().iterator();
    Long currTime = System.currentTimeMillis();
    while (containersI.hasNext()) {
      Entry<Long, List<ContainerId>> containerEntry = containersI.next();
      if (containerEntry.getKey() < currTime) {
        for (ContainerId container : containerEntry.getValue()) {
          try {
            stateStore.removeContainerToken(container);
          } catch (IOException e) {
            LOG.error("Unable to remove token for container " + container, e);
          }
        }
        containersI.remove();
      } else {
        break;
      }
    }
  }

};