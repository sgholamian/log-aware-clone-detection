//,temp,TestMaintenanceState.java,583,606,temp,TestMaintenanceState.java,292,314
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testTransitionFromDecommissionedAndExpired() throws IOException {
    LOG.info("Starting testTransitionFromDecommissionedAndExpired");
    final Path file =
        new Path("/testTransitionFromDecommissionedAndExpired.dat");

    startCluster(1, 4);

    final FileSystem fileSys = getCluster().getFileSystem(0);
    writeFile(fileSys, file, 3, 1);

    final DatanodeInfo nodeOutofService = takeNodeOutofService(0, null, 0,
        null, AdminStates.DECOMMISSIONED);

    takeNodeOutofService(0, nodeOutofService.getDatanodeUuid(),
        Long.MAX_VALUE, null, AdminStates.IN_MAINTENANCE);

    // Adjust the expiration.
    takeNodeOutofService(0, nodeOutofService.getDatanodeUuid(),
        Time.now() + EXPIRATION_IN_MS, null, AdminStates.NORMAL);

    cleanupFile(fileSys, file);
  }

};