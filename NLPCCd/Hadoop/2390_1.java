//,temp,sample_1024.java,2,18,temp,sample_3104.java,2,18
//,3
public class xxx {
private synchronized boolean checkAndStartWrite( AsyncDataService asyncDataService, WriteCtx writeCtx) {
if (writeCtx.getOffset() == nextOffset.get()) {
if (!asyncStatus) {
if (LOG.isDebugEnabled()) {
}
asyncStatus = true;
asyncWriteBackStartOffset = writeCtx.getOffset();
asyncDataService.execute(new AsyncDataService.WriteBackTask(this));
} else {
if (LOG.isDebugEnabled()) {


log.info("the write back thread is working");
}
}
}
}

};