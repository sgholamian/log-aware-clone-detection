//,temp,ActiveUsersManager.java,59,77,temp,ExcessRedundancyMap.java,77,89
//,3
public class xxx {
  @Lock({Queue.class, SchedulerApplicationAttempt.class})
  @Override
  synchronized public void activateApplication(
      String user, ApplicationId applicationId) {
    Set<ApplicationId> userApps = usersApplications.get(user);
    if (userApps == null) {
      userApps = new HashSet<ApplicationId>();
      usersApplications.put(user, userApps);
      ++activeUsers;
      metrics.incrActiveUsers();
      if (LOG.isDebugEnabled()) {
        LOG.debug("User " + user + " added to activeUsers, currently: "
            + activeUsers);
      }
    }
    if (userApps.add(applicationId)) {
      metrics.activateApp(user);
    }
  }

};