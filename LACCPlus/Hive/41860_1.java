//,temp,HadoopThriftAuthBridge.java,147,167,temp,HadoopThriftAuthBridge.java,93,112
//,3
public class xxx {
  public UserGroupInformation getCurrentUGIWithConf(String authMethod)
      throws IOException {
    UserGroupInformation ugi;
    try {
      ugi = UserGroupInformation.getCurrentUser();
    } catch(IOException e) {
      throw new IllegalStateException("Unable to get current user: " + e, e);
    }
    if (loginUserHasCurrentAuthMethod(ugi, authMethod)) {
      LOG.debug("Not setting UGI conf as passed-in authMethod of {} = current",
          authMethod);
      return ugi;
    } else {
      LOG.debug("Setting UGI conf as passed-in authMethod of {} != current",
          authMethod);
      Configuration conf = new Configuration();
      conf.set(HADOOP_SECURITY_AUTHENTICATION, authMethod);
      UserGroupInformation.setConfiguration(conf);
      return UserGroupInformation.getCurrentUser();
    }
  }

};