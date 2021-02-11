//,temp,TestDistributedShell.java,536,568,temp,TestDistributedShell.java,371,401
//,3
public class xxx {
  @Test(timeout=90000)
  public void testDSAttemptFailuresValidityIntervalSucess() throws Exception {
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
        "--attempt_failures_validity_interval",
        "2500"
      };

      LOG.info("Initializing DS Client");
      Configuration conf = yarnCluster.getConfig();
      conf.setInt(YarnConfiguration.RM_AM_MAX_ATTEMPTS, 2);
      Client client = new Client(TestDSSleepingAppMaster.class.getName(),
        new Configuration(conf));

      client.init(args);
      LOG.info("Running DS Client");
      boolean result = client.run();

      LOG.info("Client run completed. Result=" + result);
      // application should succeed
      Assert.assertTrue(result);
    }

};