//,temp,TestFsck.java,1908,2019,temp,TestFsck.java,1591,1706
//,3
public class xxx {
  @Test (timeout = 90000)
  public void testBlockIdCKMaintenance() throws Exception {
    final short replFactor = 2;
    short numDn = 2;
    final long blockSize = 512;
    String[] hosts = {"host1", "host2"};
    String[] racks = {"/rack1", "/rack2"};

    conf.setLong(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, blockSize);
    conf.setInt(DFSConfigKeys.DFS_REPLICATION_KEY, replFactor);
    conf.setInt(DFSConfigKeys.DFS_NAMENODE_REPLICATION_MIN_KEY, replFactor);
    conf.setInt(DFSConfigKeys.DFS_NAMENODE_MAINTENANCE_REPLICATION_MIN_KEY,
        replFactor);

    DistributedFileSystem dfs;
    File builderBaseDir = new File(GenericTestUtils.getRandomizedTempPath());
    cluster = new MiniDFSCluster.Builder(conf, builderBaseDir)
        .numDataNodes(numDn)
        .hosts(hosts)
        .racks(racks)
        .build();

    assertNotNull("Failed Cluster Creation", cluster);
    cluster.waitClusterUp();
    dfs = cluster.getFileSystem();
    assertNotNull("Failed to get FileSystem", dfs);

    DFSTestUtil util = new DFSTestUtil.Builder().
        setName(getClass().getSimpleName()).setNumFiles(1).build();
    //create files
    final String pathString = new String("/testfile");
    final Path path = new Path(pathString);
    util.createFile(dfs, path, 1024, replFactor, 1000L);
    util.waitReplication(dfs, path, replFactor);
    StringBuilder sb = new StringBuilder();
    for (LocatedBlock lb: util.getAllBlocks(dfs, path)){
      sb.append(lb.getBlock().getLocalBlock().getBlockName()+" ");
    }
    String[] bIds = sb.toString().split(" ");

    //make sure datanode that has replica is fine before maintenance
    String outStr = runFsck(conf, 0, true, "/",
        "-maintenance", "-blockId", bIds[0]);
    System.out.println(outStr);
    assertTrue(outStr.contains(NamenodeFsck.HEALTHY_STATUS));

    FSNamesystem fsn = cluster.getNameNode().getNamesystem();
    BlockManager bm = fsn.getBlockManager();
    DatanodeManager dnm = bm.getDatanodeManager();
    DatanodeDescriptor dn = dnm.getDatanode(cluster.getDataNodes().get(0)
        .getDatanodeId());
    bm.getDatanodeManager().getDatanodeAdminManager().startMaintenance(dn,
        Long.MAX_VALUE);
    final String dnName = dn.getXferAddr();

    //wait for the node to enter maintenance state
    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      @Override
      public Boolean get() {
        DatanodeInfo datanodeInfo = null;
        try {
          for (DatanodeInfo info : dfs.getDataNodeStats()) {
            if (dnName.equals(info.getXferAddr())) {
              datanodeInfo = info;
            }
          }
          if (datanodeInfo != null && datanodeInfo.isEnteringMaintenance()) {
            String fsckOut = runFsck(conf, 5, false, "/",
                "-maintenance", "-blockId", bIds[0]);
            assertTrue(fsckOut.contains(
                NamenodeFsck.ENTERING_MAINTENANCE_STATUS));
            return true;
          }
        } catch (Exception e) {
          LOG.warn("Unexpected exception: " + e);
          return false;
        }
        return false;
      }
    }, 500, 30000);

    // Start 3rd DataNode
    cluster.startDataNodes(conf, 1, true, null,
        new String[] {"/rack3"}, new String[] {"host3"}, null, false);

    // Wait for 1st node to reach in maintenance state
    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      @Override
      public Boolean get() {
        try {
          DatanodeInfo datanodeInfo = null;
          for (DatanodeInfo info : dfs.getDataNodeStats()) {
            if (dnName.equals(info.getXferAddr())) {
              datanodeInfo = info;
            }
          }
          if (datanodeInfo != null && datanodeInfo.isInMaintenance()) {
            return true;
          }
        } catch (Exception e) {
          LOG.warn("Unexpected exception: " + e);
          return false;
        }
        return false;
      }
    }, 500, 30000);

    //check in maintenance node
    String fsckOut = runFsck(conf, 4, false, "/",
        "-maintenance", "-blockId", bIds[0]);
    assertTrue(fsckOut.contains(NamenodeFsck.IN_MAINTENANCE_STATUS));

    //check in maintenance node are not printed when not requested
    fsckOut = runFsck(conf, 4, false, "/", "-blockId", bIds[0]);
    assertFalse(fsckOut.contains(NamenodeFsck.IN_MAINTENANCE_STATUS));
  }

};