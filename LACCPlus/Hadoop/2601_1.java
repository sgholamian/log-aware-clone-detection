//,temp,KerberosName.java,122,132,temp,KerberosName.java,86,94
//,3
public class xxx {
  public static synchronized String getDefaultRealm() {
    if (defaultRealm == null) {
      try {
        defaultRealm = KerberosUtil.getDefaultRealm();
      } catch (Exception ke) {
        LOG.debug("Kerberos krb5 configuration not found, setting default realm to empty");
        defaultRealm = "";
      }
    }
    return defaultRealm;
  }

};