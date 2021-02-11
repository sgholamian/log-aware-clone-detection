//,temp,JniBasedUnixGroupsNetgroupMapping.java,112,129,temp,JniBasedUnixGroupsMapping.java,76,89
//,3
public class xxx {
  @Override
  public List<String> getGroups(String user) throws IOException {
    String[] groups = new String[0];
    try {
      groups = getGroupsForUser(user);
    } catch (Exception e) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Error getting groups for " + user, e);
      } else {
        LOG.info("Error getting groups for " + user + ": " + e.getMessage());
      }
    }
    return Arrays.asList(groups);
  }

};