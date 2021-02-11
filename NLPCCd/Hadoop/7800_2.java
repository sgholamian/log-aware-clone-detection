//,temp,sample_5799.java,2,18,temp,sample_5798.java,2,18
//,3
public class xxx {
public void dummy_method(){
if(LOG.isDebugEnabled()) {
}
long[] oldLengths = new long[blocks.size()];
int tempLen;
for (int i = 0; i < blocks.size(); i++) {
Block b = blocks.get(i);
if(LOG.isDebugEnabled()) {
}
oldLengths[i] = b.getNumBytes();
if(LOG.isDebugEnabled()) {


log.info("setting new length");
}
}
}

};