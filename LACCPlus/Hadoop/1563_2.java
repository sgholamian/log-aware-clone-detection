//,temp,RMServerUtils.java,170,199,temp,HSAdminServer.java,164,192
//,3
public class xxx {
  private UserGroupInformation checkAcls(String method) throws IOException {
    UserGroupInformation user;
    try {
      user = UserGroupInformation.getCurrentUser();
    } catch (IOException ioe) {
      LOG.warn("Couldn't get current user", ioe);

      HSAuditLogger.logFailure("UNKNOWN", method, adminAcl.toString(),
          HISTORY_ADMIN_SERVER, "Couldn't get current user");

      throw ioe;
    }

    if (!adminAcl.isUserAllowed(user)) {
      LOG.warn("User " + user.getShortUserName() + " doesn't have permission"
          + " to call '" + method + "'");

      HSAuditLogger.logFailure(user.getShortUserName(), method,
          adminAcl.toString(), HISTORY_ADMIN_SERVER,
          AuditConstants.UNAUTHORIZED_USER);

      throw new AccessControlException("User " + user.getShortUserName()
          + " doesn't have permission" + " to call '" + method + "'");
    }
    LOG.info("HS Admin: " + method + " invoked by user "
        + user.getShortUserName());

    return user;
  }

};