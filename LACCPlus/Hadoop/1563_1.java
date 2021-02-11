//,temp,RMServerUtils.java,170,199,temp,HSAdminServer.java,164,192
//,3
public class xxx {
  public static UserGroupInformation verifyAdminAccess(
      YarnAuthorizationProvider authorizer, String method, String module,
      final Log LOG)
      throws IOException {
    UserGroupInformation user;
    try {
      user = UserGroupInformation.getCurrentUser();
    } catch (IOException ioe) {
      LOG.warn("Couldn't get current user", ioe);
      RMAuditLogger.logFailure("UNKNOWN", method, "",
          "AdminService", "Couldn't get current user");
      throw ioe;
    }

    if (!authorizer.isAdmin(user)) {
      LOG.warn("User " + user.getShortUserName() + " doesn't have permission" +
          " to call '" + method + "'");

      RMAuditLogger.logFailure(user.getShortUserName(), method, "", module,
        RMAuditLogger.AuditConstants.UNAUTHORIZED_USER);

      throw new AccessControlException("User " + user.getShortUserName() +
              " doesn't have permission" +
              " to call '" + method + "'");
    }
    if (LOG.isTraceEnabled()) {
      LOG.trace(method + " invoked by user " + user.getShortUserName());
    }
    return user;
  }

};