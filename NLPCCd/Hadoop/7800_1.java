//,temp,sample_5799.java,2,18,temp,sample_5798.java,2,18
//,3
public class xxx {
public void dummy_method(){
for (int i = 0; i < blocks.size(); i++) {
Block b = blocks.get(i);
if(LOG.isDebugEnabled()) {
}
oldLengths[i] = b.getNumBytes();
if(LOG.isDebugEnabled()) {
}
tempLen = rand.nextInt(BLOCK_SIZE);
b.set(b.getBlockId(), tempLen, b.getGenerationStamp());
if(LOG.isDebugEnabled()) {


log.info("block after t size");
}
}
}

};