//,temp,TestWebHdfsDataLocality.java,156,239,temp,TestWebHdfsDataLocality.java,73,154
//,3
public class xxx {
  @Test
  public void testDataLocality() throws Exception {
    final Configuration conf = WebHdfsTestUtil.createConf();
    final String[] racks = {RACK0, RACK0, RACK1, RACK1, RACK2, RACK2};
    final int nDataNodes = racks.length;
    LOG.info("nDataNodes=" + nDataNodes + ", racks=" + Arrays.asList(racks));

    final MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf)
        .numDataNodes(nDataNodes)
        .racks(racks)
        .build();
    try {
      cluster.waitActive();

      final DistributedFileSystem dfs = cluster.getFileSystem();
      final NameNode namenode = cluster.getNameNode();
      final DatanodeManager dm = namenode.getNamesystem().getBlockManager(
          ).getDatanodeManager();
      LOG.info("dm=" + dm);
  
      final long blocksize = DFSConfigKeys.DFS_BLOCK_SIZE_DEFAULT;
      final String f = "/foo";

      { //test CREATE
        for(int i = 0; i < nDataNodes; i++) {
          //set client address to a particular datanode
          final DataNode dn = cluster.getDataNodes().get(i);
          final String ipAddr = dm.getDatanode(dn.getDatanodeId()).getIpAddr();

          //The chosen datanode must be the same as the client address
          final DatanodeInfo chosen = NamenodeWebHdfsMethods.chooseDatanode(
              namenode, f, PutOpParam.Op.CREATE, -1L, blocksize, null,
              LOCALHOST, null);
          Assert.assertEquals(ipAddr, chosen.getIpAddr());
        }
      }
  
      //create a file with one replica.
      final Path p = new Path(f);
      final FSDataOutputStream out = dfs.create(p, (short)1);
      out.write(1);
      out.close();
  
      //get replica location.
      final LocatedBlocks locatedblocks = NameNodeAdapter.getBlockLocations(
          namenode, f, 0, 1);
      final List<LocatedBlock> lb = locatedblocks.getLocatedBlocks();
      Assert.assertEquals(1, lb.size());
      final DatanodeInfo[] locations = lb.get(0).getLocations();
      Assert.assertEquals(1, locations.length);
      final DatanodeInfo expected = locations[0];
      
      //For GETFILECHECKSUM, OPEN and APPEND,
      //the chosen datanode must be the same as the replica location.

      { //test GETFILECHECKSUM
        final HdfsFileStatus status = dfs.getClient().getFileInfo(f);
        final DatanodeInfo chosen = NamenodeWebHdfsMethods.chooseDatanode(
            namenode, f, GetOpParam.Op.GETFILECHECKSUM, -1L, blocksize, null,
            LOCALHOST, status);
        Assert.assertEquals(expected, chosen);
      }
  
      { //test OPEN
        final HdfsFileStatus status = dfs.getClient().getFileInfo(f);
        final DatanodeInfo chosen = NamenodeWebHdfsMethods.chooseDatanode(
            namenode, f, GetOpParam.Op.OPEN, 0, blocksize, null,
            LOCALHOST, status);
        Assert.assertEquals(expected, chosen);
      }

      { //test APPEND
        final HdfsFileStatus status = dfs.getClient().getFileInfo(f);
        final DatanodeInfo chosen = NamenodeWebHdfsMethods.chooseDatanode(
            namenode, f, PostOpParam.Op.APPEND, -1L, blocksize, null,
            LOCALHOST, status);
        Assert.assertEquals(expected, chosen);
      }
    } finally {
      cluster.shutdown();
    }
  }

};