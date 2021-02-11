//,temp,sample_1002.java,2,9,temp,sample_7849.java,2,9
//,3
public class xxx {
private long updateNonSequentialWriteInMemory(long count) {
long newValue = nonSequentialWriteInMemory.addAndGet(count);
if (LOG.isDebugEnabled()) {


log.info("update nonsequentialwriteinmemory by new value");
}
}

};