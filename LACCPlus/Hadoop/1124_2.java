//,temp,DataNode.java,992,1000,temp,DataNode.java,980,989
//,2
public class xxx {
  protected void notifyNamenodeReceivingBlock(
      ExtendedBlock block, String storageUuid) {
    BPOfferService bpos = blockPoolManager.get(block.getBlockPoolId());
    if(bpos != null) {
      bpos.notifyNamenodeReceivingBlock(block, storageUuid);
    } else {
      LOG.error("Cannot find BPOfferService for reporting block receiving for bpid="
          + block.getBlockPoolId());
    }
  }

};