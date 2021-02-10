//,temp,SecurityUtil.java,118,130,temp,NMTokenSecretManagerInNM.java,249,254
//,3
public class xxx {
  @InterfaceAudience.Private
  @VisibleForTesting
  public static void setTokenServiceUseIp(boolean flag) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Setting "
          + CommonConfigurationKeys.HADOOP_SECURITY_TOKEN_SERVICE_USE_IP
          + " to " + flag);
    }
    useIpForTokenService = flag;
    hostResolver = !useIpForTokenService
        ? new QualifiedHostResolver()
        : new StandardHostResolver();
  }

};