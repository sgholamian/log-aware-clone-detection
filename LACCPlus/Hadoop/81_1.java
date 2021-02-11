//,temp,TestDistributedShell.java,808,833,temp,TestDistributedShell.java,336,363
//,3
public class xxx {
  @Test(timeout=90000)
  public void testDebugFlag() throws Exception {
    String[] args = {
        "--jar",
        APPMASTER_JAR,
        "--num_containers",
        "2",
        "--shell_command",
        Shell.WINDOWS ? "dir" : "ls",
        "--master_memory",
        "512",
        "--master_vcores",
        "2",
        "--container_memory",
        "128",
        "--container_vcores",
        "1",
        "--debug"
    };

    LOG.info("Initializing DS Client");
    Client client = new Client(new Configuration(yarnCluster.getConfig()));
    Assert.assertTrue(client.init(args));
    LOG.info("Running DS Client");
    Assert.assertTrue(client.run());
  }

};