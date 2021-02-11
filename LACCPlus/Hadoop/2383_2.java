//,temp,TestFsDatasetImpl.java,805,833,temp,TestFsDatasetImpl.java,762,803
//,3
public class xxx {
  @Test(timeout = 30000)
  public void testMoveBlockFailure() {
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

      // Append to file to update its GS
      FSDataOutputStream out = fs.append(filePath, (short) 1);
      out.write(100);
      out.hflush();

      // Call finalizeNewReplica
      LOG.info("GenerationStamp of old replica: {}",
          block.getGenerationStamp());
      LOG.info("GenerationStamp of new replica: {}", fsDataSetImpl
          .getReplicaInfo(block.getBlockPoolId(), newReplicaInfo.getBlockId())
          .getGenerationStamp());
      LambdaTestUtils.intercept(IOException.class, "Generation Stamp "
              + "should be monotonically increased.",
          () -> fsDataSetImpl.finalizeNewReplica(newReplicaInfo, block));
    } catch (Exception ex) {
      LOG.info("Exception in testMoveBlockFailure ", ex);
      fail("Exception while testing testMoveBlockFailure ");
    } finally {
      if (cluster.isClusterUp()) {
        cluster.shutdown();
      }
    }
  }

};