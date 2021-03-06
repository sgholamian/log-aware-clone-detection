//,temp,TestGroupFallback.java,67,84,temp,TestGroupFallback.java,51,65
//,2
public class xxx {
  @Test
  public void testNetgroupShell() throws Exception {
    Logger.getRootLogger().setLevel(Level.DEBUG);
    Configuration conf = new Configuration();
    conf.set(CommonConfigurationKeys.HADOOP_SECURITY_GROUP_MAPPING,
        "org.apache.hadoop.security.ShellBasedUnixGroupsNetgroupMapping");

    Groups groups = new Groups(conf);

    String username = System.getProperty("user.name");
    List<String> groupList = groups.getGroups(username);

    LOG.info(username + " has GROUPS: " + groupList.toString());
    assertTrue(groupList.size() > 0);
  }

};