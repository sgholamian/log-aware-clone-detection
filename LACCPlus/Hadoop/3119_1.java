//,temp,TestMaintenanceState.java,876,901,temp,TestMaintenanceState.java,292,314
//,3
public class xxx {
  @Test(timeout = 3600000)
  public void testWriteAfterMaintenance() throws IOException {
    LOG.info("Starting testWriteAfterMaintenance");
    startCluster(1, 3);

    final Path file = new Path("/testWriteAfterMaintenance.dat");
    int replicas = 3;

    final FileSystem fileSys = getCluster().getFileSystem(0);
    FSNamesystem ns = getCluster().getNamesystem(0);

    final DatanodeInfo nodeOutofService = takeNodeOutofService(0, null,
        Long.MAX_VALUE, null, AdminStates.IN_MAINTENANCE);

    writeFile(fileSys, file, replicas, 2);

    // Verify nodeOutofService wasn't chosen for write operation.
    checkWithRetry(ns, fileSys, file, replicas - 1,
        nodeOutofService, null);

    // Put the node back to service, live replicas should be restored.
    putNodeInService(0, nodeOutofService.getDatanodeUuid());
    checkWithRetry(ns, fileSys, file, replicas, null);

    cleanupFile(fileSys, file);
  }

};