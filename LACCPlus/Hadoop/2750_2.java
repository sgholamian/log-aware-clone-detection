//,temp,TestMaintenanceState.java,413,429,temp,TestMaintenanceState.java,176,192
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testInvalidExpiration() throws Exception {
    LOG.info("Starting testInvalidExpiration");
    final int replicas = 1;
    final Path file = new Path("/testInvalidExpiration.dat");

    startCluster(1, 1);

    final FileSystem fileSys = getCluster().getFileSystem(0);
    writeFile(fileSys, file, replicas, 1);

    // expiration has to be greater than Time.now().
    takeNodeOutofService(0, null, Time.now(), null,
        AdminStates.NORMAL);

    cleanupFile(fileSys, file);
  }

};