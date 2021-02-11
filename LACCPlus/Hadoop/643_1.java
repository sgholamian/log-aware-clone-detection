//,temp,FairScheduler.java,1461,1473,temp,CapacityScheduler.java,1555,1567
//,3
public class xxx {
  @Override
  public synchronized boolean checkAccess(UserGroupInformation callerUGI,
      QueueACL acl, String queueName) {
    FSQueue queue = getQueueManager().getQueue(queueName);
    if (queue == null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("ACL not found for queue access-type " + acl
            + " for queue " + queueName);
      }
      return false;
    }
    return queue.hasAccess(acl, callerUGI);
  }

};