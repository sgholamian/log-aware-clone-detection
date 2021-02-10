//,temp,TestGroupFallback.java,51,65,temp,TestGroupFallback.java,35,49
//,2
public class xxx {
  @Test
  public void testGroupShell() throws Exception {
    Logger.getRootLogger().setLevel(Level.DEBUG);
    Configuration conf = new Configuration();
    conf.set(CommonConfigurationKeys.HADOOP_SECURITY_GROUP_MAPPING,
        "org.apache.hadoop.security.ShellBasedUnixGroupsMapping");

    Groups groups = new Groups(conf);

    String username = System.getProperty("user.name");
    List<String> groupList = groups.getGroups(username);

    LOG.info(username + " has GROUPS: " + groupList.toString());
    assertTrue(groupList.size() > 0);
  }

};