//,temp,TestMaintenanceState.java,583,606,temp,TestMaintenanceState.java,292,314
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testTransitionFromDecommissioning() throws IOException {
    LOG.info("Starting testTransitionFromDecommissioning");
    startCluster(1, 3);

    final Path file = new Path("/testTransitionFromDecommissioning.dat");
    final int replicas = 3;

    final FileSystem fileSys = getCluster().getFileSystem(0);
    final FSNamesystem ns = getCluster().getNamesystem(0);

    writeFile(fileSys, file, replicas);

    final DatanodeInfo nodeOutofService = takeNodeOutofService(0, null, 0,
        null, AdminStates.DECOMMISSION_INPROGRESS);

    takeNodeOutofService(0, nodeOutofService.getDatanodeUuid(), Long.MAX_VALUE,
        null, AdminStates.IN_MAINTENANCE);

    checkWithRetry(ns, fileSys, file, replicas - 1,
        nodeOutofService);

    cleanupFile(fileSys, file);
  }

};