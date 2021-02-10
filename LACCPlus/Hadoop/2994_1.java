//,temp,JniBasedUnixGroupsNetgroupMapping.java,112,129,temp,JniBasedUnixGroupsMapping.java,76,89
//,3
public class xxx {
  protected synchronized List<String> getUsersForNetgroup(String netgroup) {
    String[] users = null;
    try {
      // JNI code does not expect '@' at the beginning of the group name
      users = getUsersForNetgroupJNI(netgroup.substring(1));
    } catch (Exception e) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Error getting users for netgroup " + netgroup, e);
      } else {
        LOG.info("Error getting users for netgroup " + netgroup + 
            ": " + e.getMessage());
      }
    }
    if (users != null && users.length != 0) {
      return Arrays.asList(users);
    }
    return new LinkedList<String>();
  }

};