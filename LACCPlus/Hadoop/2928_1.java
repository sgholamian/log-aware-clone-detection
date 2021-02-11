//,temp,AbstractClientRequestInterceptor.java,104,125,temp,DefaultRMAdminRequestInterceptor.java,75,111
//,3
public class xxx {
  private void setupUser(String userName) {

    try {
      // Do not create a proxy user if user name matches the user name on
      // current UGI
      if (userName.equalsIgnoreCase(
          UserGroupInformation.getCurrentUser().getUserName())) {
        user = UserGroupInformation.getCurrentUser();
      } else {
        user = UserGroupInformation.createProxyUser(userName,
            UserGroupInformation.getCurrentUser());
      }
    } catch (IOException e) {
      String message = "Error while creating Router ClientRM Service for user:";
      if (user != null) {
        message += ", user: " + user;
      }

      LOG.info(message);
      throw new YarnRuntimeException(message, e);
    }
  }

};