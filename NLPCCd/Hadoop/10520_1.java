//,temp,sample_8138.java,2,12,temp,sample_8136.java,2,12
//,2
public class xxx {
public void testDSAttemptFailuresValidityIntervalFailed() throws Exception {
String[] args = {
"--jar", APPMASTER_JAR, "--num_containers", "1", "--shell_command", "sleep 8", "--master_memory", "512", "--container_memory", "128", "--attempt_failures_validity_interval", "15000" };
Configuration conf = yarnCluster.getConfig();
conf.setInt(YarnConfiguration.RM_AM_MAX_ATTEMPTS, 2);
Client client = new Client(TestDSSleepingAppMaster.class.getName(), new Configuration(conf));
client.init(args);


log.info("running ds client");
}

};