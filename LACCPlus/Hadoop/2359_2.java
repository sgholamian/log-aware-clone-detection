//,temp,TestMaintenanceState.java,583,606,temp,TestMaintenanceState.java,152,171
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testEnteringMaintenanceExpiration() throws Exception {
    LOG.info("Starting testEnteringMaintenanceExpiration");
    final int replicas = 1;
    final Path file = new Path("/testEnteringMaintenanceExpiration.dat");

    startCluster(1, 1);

    final FileSystem fileSys = getCluster().getFileSystem(0);
    writeFile(fileSys, file, replicas, 1);

    final DatanodeInfo nodeOutofService = takeNodeOutofService(0, null,
        Long.MAX_VALUE, null, AdminStates.ENTERING_MAINTENANCE);

    // Adjust the expiration.
    takeNodeOutofService(0, nodeOutofService.getDatanodeUuid(),
        Time.now() + EXPIRATION_IN_MS, null, AdminStates.NORMAL);

    cleanupFile(fileSys, file);
  }

};