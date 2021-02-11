//,temp,FSNamesystem.java,5300,5325,temp,FSNamesystem.java,2507,2525
//,3
public class xxx {
  void updatePipeline(
      String clientName, ExtendedBlock oldBlock, ExtendedBlock newBlock,
      DatanodeID[] newNodes, String[] newStorageIDs, boolean logRetryCache)
      throws IOException {
    LOG.info("updatePipeline(" + oldBlock.getLocalBlock()
             + ", newGS=" + newBlock.getGenerationStamp()
             + ", newLength=" + newBlock.getNumBytes()
             + ", newNodes=" + Arrays.asList(newNodes)
             + ", client=" + clientName
             + ")");
    waitForLoadingFSImage();
    writeLock();
    try {
      checkOperation(OperationCategory.WRITE);
      checkNameNodeSafeMode("Pipeline not updated");
      assert newBlock.getBlockId()==oldBlock.getBlockId() : newBlock + " and "
        + oldBlock + " has different block identifier";
      updatePipelineInternal(clientName, oldBlock, newBlock, newNodes,
          newStorageIDs, logRetryCache);
    } finally {
      writeUnlock();
    }
    getEditLog().logSync();
    LOG.info("updatePipeline(" + oldBlock.getLocalBlock() + " => "
        + newBlock.getLocalBlock() + ") success");
  }

};