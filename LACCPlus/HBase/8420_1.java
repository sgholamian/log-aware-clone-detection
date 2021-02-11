//,temp,AuthUtil.java,132,140,temp,RemoteProcedureDispatcher.java,175,183
//,3
public class xxx {
  private static boolean checkPrincipalMatch(Configuration conf, String loginUserName) {
    String configuredUserName = conf.get(HBASE_CLIENT_KERBEROS_PRINCIPAL);
    boolean match = configuredUserName.equals(loginUserName);
    if (!match) {
      LOG.warn("Trying to login with a different user: {}, existed user is {}.",
        configuredUserName, loginUserName);
    }
    return match;
  }

};