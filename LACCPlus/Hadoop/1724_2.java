//,temp,TestDistributedShell.java,780,806,temp,TestDistributedShell.java,409,439
//,3
public class xxx {
  @Test(timeout=90000)
  public void testDSAttemptFailuresValidityIntervalFailed() throws Exception {
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
        "15000"
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
      // application should be failed
      Assert.assertFalse(result);
    }

};