//,temp,TestGroupFallback.java,87,104,temp,TestGroupFallback.java,36,50
//,3
public class xxx {
  @Test
  public void testGroupShell() throws Exception {
    GenericTestUtils.setRootLogLevel(Level.DEBUG);
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