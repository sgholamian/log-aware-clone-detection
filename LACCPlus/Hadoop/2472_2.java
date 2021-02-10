//,temp,TestMaintenanceState.java,818,870,temp,TestMaintenanceState.java,762,806
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testTakeDeadNodeOutOfMaintenance() throws Exception {
    LOG.info("Starting testTakeDeadNodeOutOfMaintenance");
    final int numNamenodes = 1;
    final int numDatanodes = 4;
    startCluster(numNamenodes, numDatanodes);

    final Path file = new Path("/testTakeDeadNodeOutOfMaintenance.dat");
    final int replicas = 3;

    final FileSystem fileSys = getCluster().getFileSystem(0);
    final FSNamesystem ns = getCluster().getNamesystem(0);
    writeFile(fileSys, file, replicas, 1);

    final DatanodeInfo nodeOutofService = takeNodeOutofService(0,
        getFirstBlockFirstReplicaUuid(fileSys, file), Long.MAX_VALUE, null,
        AdminStates.IN_MAINTENANCE);

    checkWithRetry(ns, fileSys, file, replicas - 1,
        nodeOutofService);

    final DFSClient client = getDfsClient(0);
    assertEquals("All datanodes must be alive", numDatanodes,
        client.datanodeReport(DatanodeReportType.LIVE).length);

    getCluster().stopDataNode(nodeOutofService.getXferAddr());
    DFSTestUtil.waitForDatanodeState(
        getCluster(), nodeOutofService.getDatanodeUuid(), false, 20000);
    assertEquals("maintenance node shouldn't be alive", numDatanodes - 1,
        client.datanodeReport(DatanodeReportType.LIVE).length);

    // Dead maintenance node's blocks should remain in block map.
    checkWithRetry(ns, fileSys, file, replicas - 1,
        nodeOutofService);

    // When dead maintenance mode is transitioned to out of maintenance mode,
    // its blocks should be removed from block map.
    // This will then trigger replication to restore the live replicas back
    // to replication factor.
    putNodeInService(0, nodeOutofService.getDatanodeUuid());
    checkWithRetry(ns, fileSys, file, replicas, nodeOutofService,
        null);

    cleanupFile(fileSys, file);
  }

};