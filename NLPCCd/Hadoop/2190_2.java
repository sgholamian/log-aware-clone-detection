//,temp,sample_8584.java,2,17,temp,sample_8576.java,2,19
//,3
public class xxx {
public void dummy_method(){
for (DatanodeDescriptor dn : datanodes) {
long remaining = dn.getCacheRemaining();
for (Iterator<CachedBlock> it = dn.getPendingCached().iterator();
it.hasNext();) {
CachedBlock cblock = it.next();
BlockInfo blockInfo = blockManager. getStoredBlock(new Block(cblock.getBlockId()));
if (blockInfo == null) {
continue;
}
if (blockInfo.getNumBytes() > remaining) {


log.info("block removing from pending cached for node because it cannot fit in remaining cache size");
}
}
}
}

};