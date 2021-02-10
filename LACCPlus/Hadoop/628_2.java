//,temp,RMContainerTokenSecretManager.java,138,149,temp,NMTokenSecretManagerInRM.java,141,152
//,3
public class xxx {
  public void clearNodeSetForAttempt(ApplicationAttemptId attemptId) {
    super.writeLock.lock();
    try {
      HashSet<NodeId> nodeSet = this.appAttemptToNodeKeyMap.get(attemptId);
      if (nodeSet != null) {
        LOG.info("Clear node set for " + attemptId);
        nodeSet.clear();
      }
    } finally {
      super.writeLock.unlock();
    }
  }

};