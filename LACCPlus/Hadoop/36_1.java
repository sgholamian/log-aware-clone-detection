//,temp,FileBasedKeyStoresFactory.java,233,246,temp,LdapGroupsMapping.java,401,417
//,3
public class xxx {
  String getPassword(Configuration conf, String alias, String defaultPass) {
    String password = defaultPass;
    try {
      char[] passchars = conf.getPassword(alias);
      if (passchars != null) {
        password = new String(passchars);
      }
    }
    catch (IOException ioe) {
      LOG.warn("Exception while trying to get password for alias " + alias +
          ": " + ioe.getMessage());
    }
    return password;
  }

};