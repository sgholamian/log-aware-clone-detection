//,temp,TestMaintenanceState.java,583,606,temp,TestMaintenanceState.java,123,146
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testTakeNodeOutOfEnteringMaintenance() throws Exception {
    LOG.info("Starting testTakeNodeOutOfEnteringMaintenance");
    final int replicas = 1;
    final Path file = new Path("/testTakeNodeOutOfEnteringMaintenance.dat");

    startCluster(1, 1);

    final FileSystem fileSys = getCluster().getFileSystem(0);
    final FSNamesystem ns = getCluster().getNamesystem(0);
    writeFile(fileSys, file, replicas, 1);

    final DatanodeInfo nodeOutofService = takeNodeOutofService(0,
        null, Long.MAX_VALUE, null, AdminStates.ENTERING_MAINTENANCE);

    // When node is in ENTERING_MAINTENANCE state, it can still serve read
    // requests
    checkWithRetry(ns, fileSys, file, replicas, null,
        nodeOutofService);

    putNodeInService(0, nodeOutofService.getDatanodeUuid());

    cleanupFile(fileSys, file);
  }

};