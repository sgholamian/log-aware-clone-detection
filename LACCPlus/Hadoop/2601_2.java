//,temp,KerberosName.java,122,132,temp,KerberosName.java,86,94
//,3
public class xxx {
  @VisibleForTesting
  public static void resetDefaultRealm() {
    try {
      defaultRealm = KerberosUtil.getDefaultRealm();
    } catch (Exception ke) {
      LOG.debug("resetting default realm failed, "
          + "current default realm will still be used.", ke);
    }
  }

};