//,temp,DataNode.java,2234,2243,temp,DataNode.java,968,977
//,3
public class xxx {
  void closeBlock(ExtendedBlock block, String delHint, String storageUuid) {
    metrics.incrBlocksWritten();
    BPOfferService bpos = blockPoolManager.get(block.getBlockPoolId());
    if(bpos != null) {
      bpos.notifyNamenodeReceivedBlock(block, delHint, storageUuid);
    } else {
      LOG.warn("Cannot find BPOfferService for reporting block received for bpid="
          + block.getBlockPoolId());
    }
  }

};