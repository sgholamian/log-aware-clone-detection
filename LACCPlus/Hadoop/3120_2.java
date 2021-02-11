//,temp,TestMaintenanceState.java,876,901,temp,TestMaintenanceState.java,269,286
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testTransitionFromDecommissioned() throws IOException {
    LOG.info("Starting testTransitionFromDecommissioned");
    final Path file = new Path("/testTransitionFromDecommissioned.dat");

    startCluster(1, 4);

    final FileSystem fileSys = getCluster().getFileSystem(0);
    writeFile(fileSys, file, 3, 1);

    DatanodeInfo nodeOutofService = takeNodeOutofService(0, null, 0, null,
        AdminStates.DECOMMISSIONED);

    takeNodeOutofService(0, nodeOutofService.getDatanodeUuid(), Long.MAX_VALUE,
        null, AdminStates.IN_MAINTENANCE);

    cleanupFile(fileSys, file);
  }

};