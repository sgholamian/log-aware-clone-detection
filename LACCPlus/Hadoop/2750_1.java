//,temp,TestMaintenanceState.java,413,429,temp,TestMaintenanceState.java,176,192
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testZeroMinMaintenanceReplication() throws Exception {
    LOG.info("Starting testZeroMinMaintenanceReplication");
    setMinMaintenanceR(0);
    startCluster(1, 1);

    final Path file = new Path("/testZeroMinMaintenanceReplication.dat");
    final int replicas = 1;

    FileSystem fileSys = getCluster().getFileSystem(0);
    writeFile(fileSys, file, replicas, 1);

    takeNodeOutofService(0, null, Long.MAX_VALUE, null,
        AdminStates.IN_MAINTENANCE);

    cleanupFile(fileSys, file);
  }

};