//,temp,TestFsck.java,1908,2019,temp,TestFsck.java,1591,1706
//,3
public class xxx {
  @Test (timeout = 90000)
  public void testFsckWithMaintenanceReplicas() throws Exception {
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
    final String testFile = new String("/testfile");
    final Path path = new Path(testFile);
    util.createFile(dfs, path, 1024, replFactor, 1000L);
    util.waitReplication(dfs, path, replFactor);
    StringBuilder sb = new StringBuilder();
    for (LocatedBlock lb: util.getAllBlocks(dfs, path)){
      sb.append(lb.getBlock().getLocalBlock().getBlockName()+" ");
    }
    String[] bIds = sb.toString().split(" ");

    //make sure datanode that has replica is fine before maintenance
    String outStr = runFsck(conf, 0, true, testFile);
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
            // verify fsck returns Healthy status
            String fsckOut = runFsck(conf, 0, true, testFile, "-maintenance");
            assertTrue(fsckOut.contains(NamenodeFsck.HEALTHY_STATUS));
            return true;
          }
        } catch (Exception e) {
          LOG.warn("Unexpected exception: " + e);
          return false;
        }
        return false;
      }
    }, 500, 30000);

    // Start 3rd DataNode and wait for node to reach in maintenance state
    cluster.startDataNodes(conf, 1, true, null,
        new String[] {"/rack3"}, new String[] {"host3"}, null, false);

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

    // verify fsck returns Healthy status
    String fsckOut = runFsck(conf, 0, true, testFile, "-maintenance");
    assertTrue(fsckOut.contains(NamenodeFsck.HEALTHY_STATUS));

    // verify fsck returns Healthy status even without maintenance option
    fsckOut = runFsck(conf, 0, true, testFile);
    assertTrue(fsckOut.contains(NamenodeFsck.HEALTHY_STATUS));
  }

};