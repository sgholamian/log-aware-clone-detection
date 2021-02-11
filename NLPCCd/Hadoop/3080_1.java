//,temp,sample_1986.java,2,11,temp,sample_1979.java,2,10
//,3
public class xxx {
public void blockReceivedAndDeleted(final DatanodeRegistration nodeReg, String poolId, StorageReceivedDeletedBlocks[] receivedAndDeletedBlocks) throws IOException {
checkNNStartup();
verifyRequest(nodeReg);
metrics.incrBlockReceivedAndDeletedOps();
if(blockStateChangeLog.isDebugEnabled()) {


log.info("block namenode blockreceivedanddeleted from blocks");
}
}

};