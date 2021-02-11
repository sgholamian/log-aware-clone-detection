//,temp,QueueACLsManager.java,102,144,temp,QueueACLsManager.java,60,86
//,3
public class xxx {
  public boolean checkAccess(UserGroupInformation callerUGI, QueueACL acl,
      RMApp app, String remoteAddress, List<String> forwardedAddresses,
      String targetQueue) {
    if (!isACLsEnable) {
      return true;
    }

    // Based on the discussion in YARN-5554 detail on why there are two
    // versions:
    // The access check inside these calls is currently scheduler dependent.
    // This is due to the extra parameters needed for the CS case which are not
    // in the version defined in the YarnScheduler interface. The second
    // version is added for the moving the application case. The check has
    // extra logging to distinguish between the queue not existing in the
    // application move request case and the real access denied case.
    if (scheduler instanceof CapacityScheduler) {
      CSQueue queue = ((CapacityScheduler) scheduler).getQueue(targetQueue);
      if (queue == null) {
        LOG.warn("Target queue " + targetQueue
            + " does not exist while trying to move "
            + app.getApplicationId());
        return false;
      }
      return authorizer.checkPermission(
          new AccessRequest(queue.getPrivilegedEntity(), callerUGI,
              SchedulerUtils.toAccessType(acl),
              app.getApplicationId().toString(), app.getName(),
              remoteAddress, forwardedAddresses));
    } else if (scheduler instanceof FairScheduler) {
      FSQueue queue = ((FairScheduler) scheduler).getQueueManager().
          getQueue(targetQueue);
      if (queue == null) {
        LOG.warn("Target queue " + targetQueue
            + " does not exist while trying to move "
            + app.getApplicationId());
        return false;
      }
      return scheduler.checkAccess(callerUGI, acl, targetQueue);
    } else {
      // Any other scheduler just try
      return scheduler.checkAccess(callerUGI, acl, targetQueue);
    }
  }

};