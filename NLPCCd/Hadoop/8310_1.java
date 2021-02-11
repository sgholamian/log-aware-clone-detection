//,temp,sample_5212.java,2,12,temp,sample_4268.java,2,14
//,3
public class xxx {
public synchronized void disableBlockPoolId(String bpid) {
Iterator<BlockIterator> i = blockIters.iterator();
while (i.hasNext()) {
BlockIterator iter = i.next();
if (iter.getBlockPoolId().equals(bpid)) {


log.info("disabling scanning on block pool");
}
}
}

};