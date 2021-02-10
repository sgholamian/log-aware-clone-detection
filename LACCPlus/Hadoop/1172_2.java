//,temp,DataNode.java,980,989,temp,DataNode.java,968,977
//,3
public class xxx {
  public void notifyNamenodeReceivedBlock(
      ExtendedBlock block, String delHint, String storageUuid) {
    BPOfferService bpos = blockPoolManager.get(block.getBlockPoolId());
    if(bpos != null) {
      bpos.notifyNamenodeReceivedBlock(block, delHint, storageUuid);
    } else {
      LOG.error("Cannot find BPOfferService for reporting block received for bpid="
          + block.getBlockPoolId());
    }
  }

};