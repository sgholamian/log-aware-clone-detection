//,temp,TestMaintenanceState.java,715,751,temp,TestMaintenanceState.java,539,578
//,3
public class xxx {
  private void testChangeReplicationFactor(int oldFactor, int newFactor,
      int expectedLiveReplicas) throws IOException {
    setup();
    LOG.info("Starting testChangeReplicationFactor {} {} {}",
        oldFactor, newFactor, expectedLiveReplicas);
    startCluster(1, 5);

    final Path file = new Path("/testChangeReplicationFactor.dat");

    final FileSystem fileSys = getCluster().getFileSystem(0);
    final FSNamesystem ns = getCluster().getNamesystem(0);

    writeFile(fileSys, file, oldFactor, 1);

    final DatanodeInfo nodeOutofService = takeNodeOutofService(0,
        getFirstBlockFirstReplicaUuid(fileSys, file), Long.MAX_VALUE, null,
        AdminStates.IN_MAINTENANCE);

    // Verify that the nodeOutofService remains in blocksMap and
    // # of live replicas For read operation is expected.
    checkWithRetry(ns, fileSys, file, oldFactor - 1,
        nodeOutofService);

    final DFSClient client = getDfsClient(0);
    client.setReplication(file.toString(), (short)newFactor);

    // Verify that the nodeOutofService remains in blocksMap and
    // # of live replicas for read operation.
    checkWithRetry(ns, fileSys, file, expectedLiveReplicas,
        nodeOutofService);

    putNodeInService(0, nodeOutofService.getDatanodeUuid());
    checkWithRetry(ns, fileSys, file, newFactor, null);

    cleanupFile(fileSys, file);
    teardown();
  }

};