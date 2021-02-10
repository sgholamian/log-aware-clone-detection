//,temp,TestGroupFallback.java,87,104,temp,TestGroupFallback.java,52,66
//,3
public class xxx {
  @Test
  public void testNetgroupShell() throws Exception {
    GenericTestUtils.setRootLogLevel(Level.DEBUG);
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