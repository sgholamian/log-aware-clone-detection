//,temp,RegistrySecurity.java,1081,1089,temp,LocalJavaKeyStoreProvider.java,66,73
//,3
public class xxx {
  public ACL createACLfromUsername(String username, int perms) {
    if (usesRealm && !username.contains("@")) {
      username = username + "@" + kerberosRealm;
      if (LOG.isDebugEnabled()) {
        LOG.debug("Appending kerberos realm to make {}", username);
      }
    }
    return new ACL(perms, new Id(SCHEME_SASL, username));
  }

};