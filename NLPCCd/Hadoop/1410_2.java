//,temp,sample_4681.java,8,15,temp,sample_1002.java,2,9
//,3
public class xxx {
private long updateNonSequentialWriteInMemory(long count) {
long newValue = nonSequentialWriteInMemory.addAndGet(count);
if (LOG.isDebugEnabled()) {


log.info("update nonsequentialwriteinmemory by new value");
}
}

};