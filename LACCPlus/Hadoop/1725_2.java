//,temp,TestDistributedShell.java,808,833,temp,TestDistributedShell.java,780,806
//,3
public class xxx {
  @Test(timeout=90000)
  public void testContainerLaunchFailureHandling() throws Exception {
    String[] args = {
      "--jar",
      APPMASTER_JAR,
      "--num_containers",
      "2",
      "--shell_command",
      Shell.WINDOWS ? "dir" : "ls",
      "--master_memory",
      "512",
      "--container_memory",
      "128"
    };

    LOG.info("Initializing DS Client");
    Client client = new Client(ContainerLaunchFailAppMaster.class.getName(),
      new Configuration(yarnCluster.getConfig()));
    boolean initSuccess = client.init(args);
    Assert.assertTrue(initSuccess);
    LOG.info("Running DS Client");
    boolean result = client.run();

    LOG.info("Client run completed. Result=" + result);
    Assert.assertFalse(result);

  }

};