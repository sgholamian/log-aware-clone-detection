//,temp,SecurityUtil.java,118,130,temp,CGroupsResourceCalculator.java,146,152
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