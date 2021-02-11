//,temp,sample_8584.java,2,17,temp,sample_8576.java,2,19
//,3
public class xxx {
public void dummy_method(){
it = datanode.getPendingUncached().iterator();
while (it.hasNext()) {
CachedBlock cBlock = it.next();
BlockInfo info = blockManager.getStoredBlock(new Block(cBlock.getBlockId()));
if (info != null) {
pendingBytes += info.getNumBytes();
}
}
long pendingCapacity = pendingBytes + datanode.getCacheRemaining();
if (pendingCapacity < blockInfo.getNumBytes()) {


log.info("block datanode is not a valid possibility because the block has size but the datanode only has bytes of cache remaining pending bytes already cached");
}
}

};