//,temp,TargetedDataFileAppender.java,275,294,temp,DataFileAppender.java,381,402
//,3
public class xxx {
    protected void signalDone(WriteBatch wb) {
        // Now that the data is on disk, remove the writes from the in
        // flight
        // cache.
        Journal.WriteCommand write = wb.writes.getHead();
        while (write != null) {
            if (!write.sync) {
                inflightWrites.remove(new Journal.WriteKey(write.location));
            }
            if (write.onComplete != null && wb.exception.get() == null) {
                try {
                    write.onComplete.run();
                } catch (Throwable e) {
                    logger.info("Add exception was raised while executing the run command for onComplete", e);
                }
            }
            write = write.getNext();
        }

        // Signal any waiting threads that the write is on disk.
        wb.latch.countDown();
    }

};