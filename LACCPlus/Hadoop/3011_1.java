//,temp,FairScheduler.java,1573,1590,temp,DomainSocketWatcher.java,266,284
//,3
public class xxx {
  @Override
  public boolean checkAccess(UserGroupInformation callerUGI,
      QueueACL acl, String queueName) {
    readLock.lock();
    try {
      FSQueue queue = getQueueManager().getQueue(queueName);
      if (queue == null) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("ACL not found for queue access-type " + acl + " for queue "
              + queueName);
        }
        return false;
      }
      return queue.hasAccess(acl, callerUGI);
    } finally {
      readLock.unlock();
    }
  }

};