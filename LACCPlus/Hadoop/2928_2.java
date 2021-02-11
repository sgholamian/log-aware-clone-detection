//,temp,AbstractClientRequestInterceptor.java,104,125,temp,DefaultRMAdminRequestInterceptor.java,75,111
//,3
public class xxx {
  @Override
  public void init(String userName) {
    super.init(userName);
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

      final Configuration conf = this.getConf();

      rmAdminProxy = user.doAs(
          new PrivilegedExceptionAction<ResourceManagerAdministrationProtocol>() {
            @Override
            public ResourceManagerAdministrationProtocol run()
                throws Exception {
              return ClientRMProxy.createRMProxy(conf,
                  ResourceManagerAdministrationProtocol.class);
            }
          });
    } catch (IOException e) {
      String message = "Error while creating Router RMAdmin Service for user:";
      if (user != null) {
        message += ", user: " + user;
      }

      LOG.info(message);
      throw new YarnRuntimeException(message, e);
    } catch (Exception e) {
      throw new YarnRuntimeException(e);
    }
  }

};