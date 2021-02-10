//,temp,NameNodeRpcServer.java,1338,1352,temp,NameNodeRpcServer.java,1325,1336
//,3
public class xxx {
  @Override // DatanodeProtocol
  public void blockReceivedAndDeleted(DatanodeRegistration nodeReg, String poolId,
      StorageReceivedDeletedBlocks[] receivedAndDeletedBlocks) throws IOException {
    checkNNStartup();
    verifyRequest(nodeReg);
    metrics.incrBlockReceivedAndDeletedOps();
    if(blockStateChangeLog.isDebugEnabled()) {
      blockStateChangeLog.debug("*BLOCK* NameNode.blockReceivedAndDeleted: "
          +"from "+nodeReg+" "+receivedAndDeletedBlocks.length
          +" blocks.");
    }
    for(StorageReceivedDeletedBlocks r : receivedAndDeletedBlocks) {
      namesystem.processIncrementalBlockReport(nodeReg, r);
    }
  }

};