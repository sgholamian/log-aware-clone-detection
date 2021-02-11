//,temp,TestMaintenanceState.java,876,901,temp,TestMaintenanceState.java,436,457
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testZeroMinMaintenanceReplicationWithExpiration()
      throws Exception {
    LOG.info("Starting testZeroMinMaintenanceReplicationWithExpiration");
    setMinMaintenanceR(0);
    startCluster(1, 1);

    final Path file =
        new Path("/testZeroMinMaintenanceReplicationWithExpiration.dat");

    FileSystem fileSys = getCluster().getFileSystem(0);
    writeFile(fileSys, file, 1, 1);

    DatanodeInfo nodeOutofService = takeNodeOutofService(0, null,
        Long.MAX_VALUE, null, AdminStates.IN_MAINTENANCE);

    // Adjust the expiration.
    takeNodeOutofService(0, nodeOutofService.getDatanodeUuid(),
        Time.now() + EXPIRATION_IN_MS, null, AdminStates.NORMAL);

    cleanupFile(fileSys, file);
  }

};