//,temp,TestMaintenanceState.java,927,957,temp,TestMaintenanceState.java,715,751
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testInvalidation() throws IOException {
    LOG.info("Starting testInvalidation");
    int numNamenodes = 1;
    int numDatanodes = 3;
    startCluster(numNamenodes, numDatanodes);

    Path file = new Path("/testInvalidation.dat");
    int replicas = 3;

    FileSystem fileSys = getCluster().getFileSystem(0);
    FSNamesystem ns = getCluster().getNamesystem(0);

    writeFile(fileSys, file, replicas);

    DatanodeInfo nodeOutofService = takeNodeOutofService(0, null,
        Long.MAX_VALUE, null, AdminStates.IN_MAINTENANCE);

    DFSClient client = getDfsClient(0);
    client.setReplication(file.toString(), (short) 1);

    // Verify the nodeOutofService remains in blocksMap.
    checkWithRetry(ns, fileSys, file, 1, nodeOutofService);

    // Restart NN and verify the nodeOutofService remains in blocksMap.
    getCluster().restartNameNode(0);
    ns = getCluster().getNamesystem(0);
    checkWithRetry(ns, fileSys, file, 1, nodeOutofService);

    cleanupFile(fileSys, file);
  }

};