//,temp,sample_8260.java,2,12,temp,sample_1002.java,2,9
//,3
public class xxx {
private long updateNonSequentialWriteInMemory(long count) {
long newValue = nonSequentialWriteInMemory.addAndGet(count);
if (LOG.isDebugEnabled()) {


log.info("update nonsequentialwriteinmemory by new value");
}
}

};