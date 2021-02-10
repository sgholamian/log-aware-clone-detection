//,temp,TestFsDatasetImpl.java,805,833,temp,TestFsDatasetImpl.java,762,803
//,3
public class xxx {
  @Test(timeout = 30000)
  public void testMoveBlockSuccess() {
    MiniDFSCluster cluster = null;
    try {
      cluster = new MiniDFSCluster.Builder(conf)
          .numDataNodes(1)
          .storageTypes(new StorageType[]{StorageType.DISK, StorageType.DISK})
          .storagesPerDatanode(2)
          .build();
      FileSystem fs = cluster.getFileSystem();
      DataNode dataNode = cluster.getDataNodes().get(0);

      Path filePath = new Path("testData");
      DFSTestUtil.createFile(fs, filePath, 100, (short) 1, 0);
      ExtendedBlock block = DFSTestUtil.getFirstBlock(fs, filePath);

      FsDatasetImpl fsDataSetImpl = (FsDatasetImpl) dataNode.getFSDataset();
      ReplicaInfo newReplicaInfo = createNewReplicaObj(block, fsDataSetImpl);
      fsDataSetImpl.finalizeNewReplica(newReplicaInfo, block);

    } catch (Exception ex) {
      LOG.info("Exception in testMoveBlockSuccess ", ex);
      fail("MoveBlock operation should succeed");
    } finally {
      if (cluster.isClusterUp()) {
        cluster.shutdown();
      }
    }
  }

};