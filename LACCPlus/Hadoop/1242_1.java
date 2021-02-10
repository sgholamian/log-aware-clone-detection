//,temp,TestDistributedShell.java,536,568,temp,TestDistributedShell.java,336,363
//,3
public class xxx {
  @Test(timeout=90000)
  public void testDSShellWithMultipleArgs() throws Exception {
    String[] args = {
        "--jar",
        APPMASTER_JAR,
        "--num_containers",
        "4",
        "--shell_command",
        "echo",
        "--shell_args",
        "HADOOP YARN MAPREDUCE HDFS",
        "--master_memory",
        "512",
        "--master_vcores",
        "2",
        "--container_memory",
        "128",
        "--container_vcores",
        "1"
    };

    LOG.info("Initializing DS Client");
    final Client client =
        new Client(new Configuration(yarnCluster.getConfig()));
    boolean initSuccess = client.init(args);
    Assert.assertTrue(initSuccess);
    LOG.info("Running DS Client");
    boolean result = client.run();
    LOG.info("Client run completed. Result=" + result);
    List<String> expectedContent = new ArrayList<String>();
    expectedContent.add("HADOOP YARN MAPREDUCE HDFS");
    verifyContainerLog(4, expectedContent, false, "");
  }

};