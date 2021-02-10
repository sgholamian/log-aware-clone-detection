//,temp,QueueACLsManager.java,102,144,temp,QueueACLsManager.java,60,86
//,3
public class xxx {
  public boolean checkAccess(UserGroupInformation callerUGI, QueueACL acl,
      RMApp app, String remoteAddress, List<String> forwardedAddresses) {
    if (!isACLsEnable) {
      return true;
    }

    if (scheduler instanceof CapacityScheduler) {
      CSQueue queue = ((CapacityScheduler) scheduler).getQueue(app.getQueue());
      if (queue == null) {
        // The application exists but the associated queue does not exist.
        // This may be due to a queue that is not defined when the RM restarts.
        // At this point we choose to log the fact and allow users to access
        // and view the apps in a removed queue. This should only happen on
        // application recovery.
        LOG.error("Queue " + app.getQueue() + " does not exist for " + app
            .getApplicationId());
        return true;
      }
      return authorizer.checkPermission(
          new AccessRequest(queue.getPrivilegedEntity(), callerUGI,
              SchedulerUtils.toAccessType(acl),
              app.getApplicationId().toString(), app.getName(),
              remoteAddress, forwardedAddresses));
    } else {
      return scheduler.checkAccess(callerUGI, acl, app.getQueue());
    }
  }

};