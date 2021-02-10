//,temp,TestMaintenanceState.java,818,870,temp,TestMaintenanceState.java,762,806
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testWithNNAndDNRestart() throws Exception {
    LOG.info("Starting testWithNNAndDNRestart");
    final int numNamenodes = 1;
    final int numDatanodes = 4;
    startCluster(numNamenodes, numDatanodes);

    final Path file = new Path("/testWithNNAndDNRestart.dat");
    final int replicas = 3;

    final FileSystem fileSys = getCluster().getFileSystem(0);
    FSNamesystem ns = getCluster().getNamesystem(0);
    writeFile(fileSys, file, replicas, 1);

    DatanodeInfo nodeOutofService = takeNodeOutofService(0,
        getFirstBlockFirstReplicaUuid(fileSys, file), Long.MAX_VALUE, null,
        AdminStates.IN_MAINTENANCE);

    checkWithRetry(ns, fileSys, file, replicas - 1,
        nodeOutofService);

    DFSClient client = getDfsClient(0);
    assertEquals("All datanodes must be alive", numDatanodes,
        client.datanodeReport(DatanodeReportType.LIVE).length);

    MiniDFSCluster.DataNodeProperties dnProp =
        getCluster().stopDataNode(nodeOutofService.getXferAddr());
    DFSTestUtil.waitForDatanodeState(
        getCluster(), nodeOutofService.getDatanodeUuid(), false, 20000);
    assertEquals("maintenance node shouldn't be alive", numDatanodes - 1,
        client.datanodeReport(DatanodeReportType.LIVE).length);

    // Dead maintenance node's blocks should remain in block map.
    checkWithRetry(ns, fileSys, file, replicas - 1,
        nodeOutofService);

    // restart nn, nn will restore 3 live replicas given it doesn't
    // know the maintenance node has the replica.
    getCluster().restartNameNode(0);
    ns = getCluster().getNamesystem(0);
    checkWithRetry(ns, fileSys, file, replicas, null);

    // restart dn, nn has 1 maintenance replica and 3 live replicas.
    getCluster().restartDataNode(dnProp, true);
    getCluster().waitActive();
    checkWithRetry(ns, fileSys, file, replicas, nodeOutofService);

    // Put the node in service, a redundant replica should be removed.
    putNodeInService(0, nodeOutofService.getDatanodeUuid());
    checkWithRetry(ns, fileSys, file, replicas, null);

    cleanupFile(fileSys, file);
  }

};