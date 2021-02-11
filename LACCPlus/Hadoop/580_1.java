//,temp,TestGroupFallback.java,67,84,temp,TestGroupFallback.java,35,49
//,2
public class xxx {
  @Test
  public void testGroupWithFallback() throws Exception {
    LOG.info("running 'mvn -Pnative -DTestGroupFallback clear test' will " +
        "test the normal path and 'mvn -DTestGroupFallback clear test' will" +
        " test the fall back functionality");
    Logger.getRootLogger().setLevel(Level.DEBUG);
    Configuration conf = new Configuration();
    conf.set(CommonConfigurationKeys.HADOOP_SECURITY_GROUP_MAPPING,
        "org.apache.hadoop.security.JniBasedUnixGroupsMappingWithFallback");

    Groups groups = new Groups(conf);

    String username = System.getProperty("user.name");
    List<String> groupList = groups.getGroups(username);

    LOG.info(username + " has GROUPS: " + groupList.toString());
    assertTrue(groupList.size() > 0);
  }

};