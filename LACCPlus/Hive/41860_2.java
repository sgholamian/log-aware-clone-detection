//,temp,HadoopThriftAuthBridge.java,147,167,temp,HadoopThriftAuthBridge.java,93,112
//,3
public class xxx {
  public Client createClientWithConf(String authMethod) {
    UserGroupInformation ugi;
    try {
      ugi = UserGroupInformation.getLoginUser();
    } catch(IOException e) {
      throw new IllegalStateException("Unable to get current login user: " + e, e);
    }
    if (loginUserHasCurrentAuthMethod(ugi, authMethod)) {
      LOG.debug("Not setting UGI conf as passed-in authMethod of {} = current",
          authMethod);
      return new Client();
    } else {
      LOG.debug("Setting UGI conf as passed-in authMethod of {} != current",
          authMethod);
      Configuration conf = new Configuration();
      conf.set(HADOOP_SECURITY_AUTHENTICATION, authMethod);
      UserGroupInformation.setConfiguration(conf);
      return new Client();
    }
  }

};