//,temp,TestMaintenanceState.java,715,751,temp,TestMaintenanceState.java,539,578
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testTransitionToDecommission() throws IOException {
    LOG.info("Starting testTransitionToDecommission");
    final int numNamenodes = 1;
    final int numDatanodes = 4;
    startCluster(numNamenodes, numDatanodes);

    final Path file = new Path("testTransitionToDecommission.dat");
    final int replicas = 3;

    FileSystem fileSys = getCluster().getFileSystem(0);
    FSNamesystem ns = getCluster().getNamesystem(0);

    writeFile(fileSys, file, replicas, 25);

    DatanodeInfo nodeOutofService = takeNodeOutofService(0,
        getFirstBlockFirstReplicaUuid(fileSys, file), Long.MAX_VALUE, null,
        AdminStates.IN_MAINTENANCE);

    DFSClient client = getDfsClient(0);
    assertEquals("All datanodes must be alive", numDatanodes,
        client.datanodeReport(DatanodeReportType.LIVE).length);

    // test 1, verify the replica in IN_MAINTENANCE state isn't in LocatedBlock
    checkWithRetry(ns, fileSys, file, replicas - 1,
        nodeOutofService);

    takeNodeOutofService(0, nodeOutofService.getDatanodeUuid(), 0, null,
        AdminStates.DECOMMISSIONED);

    // test 2 after decommission has completed, the replication count is
    // replicas + 1 which includes the decommissioned node.
    checkWithRetry(ns, fileSys, file, replicas + 1, null);

    // test 3, put the node in service, replication count should restore.
    putNodeInService(0, nodeOutofService.getDatanodeUuid());
    checkWithRetry(ns, fileSys, file, replicas, null);

    cleanupFile(fileSys, file);
  }

};