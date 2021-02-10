//,temp,TestDistributedShell.java,536,568,temp,TestDistributedShell.java,504,534
//,3
public class xxx {
  public void testDSShellWithCommands() throws Exception {

    String[] args = {
        "--jar",
        APPMASTER_JAR,
        "--num_containers",
        "2",
        "--shell_command",
        "\"echo output_ignored;echo output_expected\"",
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
    expectedContent.add("output_expected");
    verifyContainerLog(2, expectedContent, false, "");
  }

};