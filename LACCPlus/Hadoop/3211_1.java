//,temp,TestWebHdfsDataLocality.java,156,239,temp,TestWebHdfsDataLocality.java,73,154
//,3
public class xxx {
  @Test
  public void testExcludeDataNodes() throws Exception {
    final Configuration conf = WebHdfsTestUtil.createConf();
    final String[] racks = {RACK0, RACK0, RACK1, RACK1, RACK2, RACK2};
    final String[] hosts = {"DataNode1", "DataNode2", "DataNode3","DataNode4","DataNode5","DataNode6"};
    final int nDataNodes = hosts.length;
    LOG.info("nDataNodes=" + nDataNodes + ", racks=" + Arrays.asList(racks)
        + ", hosts=" + Arrays.asList(hosts));

    final MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf)
        .hosts(hosts).numDataNodes(nDataNodes).racks(racks).build();
    
    try {
      cluster.waitActive();

      final DistributedFileSystem dfs = cluster.getFileSystem();
      final NameNode namenode = cluster.getNameNode();
      final DatanodeManager dm = namenode.getNamesystem().getBlockManager(
          ).getDatanodeManager();
      LOG.info("dm=" + dm);
  
      final long blocksize = DFSConfigKeys.DFS_BLOCK_SIZE_DEFAULT;
      final String f = "/foo";
      
      //create a file with three replica.
      final Path p = new Path(f);
      final FSDataOutputStream out = dfs.create(p, (short)3);
      out.write(1);
      out.close(); 
      
      //get replica location.
      final LocatedBlocks locatedblocks = NameNodeAdapter.getBlockLocations(
          namenode, f, 0, 1);
      final List<LocatedBlock> lb = locatedblocks.getLocatedBlocks();
      Assert.assertEquals(1, lb.size());
      final DatanodeInfo[] locations = lb.get(0).getLocations();
      Assert.assertEquals(3, locations.length);
      
      
      //For GETFILECHECKSUM, OPEN and APPEND,
      //the chosen datanode must be different with exclude nodes.

      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < 2; i++) {
        sb.append(locations[i].getXferAddr());
        { // test GETFILECHECKSUM
          final HdfsFileStatus status = dfs.getClient().getFileInfo(f);
          final DatanodeInfo chosen = NamenodeWebHdfsMethods.chooseDatanode(
              namenode, f, GetOpParam.Op.GETFILECHECKSUM, -1L, blocksize,
              sb.toString(), LOCALHOST, status);
          for (int j = 0; j <= i; j++) {
            Assert.assertNotEquals(locations[j].getHostName(),
                chosen.getHostName());
          }
        }

        { // test OPEN
          final HdfsFileStatus status = dfs.getClient().getFileInfo(f);
          final DatanodeInfo chosen = NamenodeWebHdfsMethods.chooseDatanode(
              namenode, f, GetOpParam.Op.OPEN, 0, blocksize, sb.toString(),
              LOCALHOST, status);
          for (int j = 0; j <= i; j++) {
            Assert.assertNotEquals(locations[j].getHostName(),
                chosen.getHostName());
          }
        }
  
        { // test APPEND
          final HdfsFileStatus status = dfs.getClient().getFileInfo(f);
          final DatanodeInfo chosen = NamenodeWebHdfsMethods
              .chooseDatanode(namenode, f, PostOpParam.Op.APPEND, -1L,
                  blocksize, sb.toString(), LOCALHOST, status);
          for (int j = 0; j <= i; j++) {
            Assert.assertNotEquals(locations[j].getHostName(),
                chosen.getHostName());
          }
        }
        
        sb.append(",");
      }
    } finally {
      cluster.shutdown();
    }
  }

};