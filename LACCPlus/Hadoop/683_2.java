//,temp,TestDistributedShell.java,780,806,temp,TestDistributedShell.java,336,363
//,3
public class xxx {
  @Test(timeout=90000)
  public void testDSRestartWithPreviousRunningContainers() throws Exception {
    String[] args = {
        "--jar",
        APPMASTER_JAR,
        "--num_containers",
        "1",
        "--shell_command",
        "sleep 8",
        "--master_memory",
        "512",
        "--container_memory",
        "128",
        "--keep_containers_across_application_attempts"
      };

      LOG.info("Initializing DS Client");
      Client client = new Client(TestDSFailedAppMaster.class.getName(),
        new Configuration(yarnCluster.getConfig()));

      client.init(args);
      LOG.info("Running DS Client");
      boolean result = client.run();

      LOG.info("Client run completed. Result=" + result);
      // application should succeed
      Assert.assertTrue(result);
    }

};