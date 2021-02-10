//,temp,NfsExports.java,257,273,temp,RegistrySecurity.java,320,334
//,3
public class xxx {
  public boolean addDigestACL(ACL acl) {
    if (secureRegistry) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Added ACL {}", aclToString(acl));
      }
      digestACLs.add(acl);
      return true;
    } else {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Ignoring added ACL - registry is insecure{}",
            aclToString(acl));
      }
      return false;
    }
  }

};