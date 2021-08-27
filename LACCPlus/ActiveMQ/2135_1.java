//,temp,TargetedDataFileAppender.java,275,294,temp,DataFileAppender.java,381,402
//,3
public class xxx {
    private void signalDone(WriteBatch writeBatch) {
        // Now that the data is on disk, remove the writes from the in
        // flight cache and signal any onComplete requests.
        Journal.WriteCommand write = writeBatch.writes.getHead();
        while (write != null) {
            if (!write.sync) {
                inflightWrites.remove(new Journal.WriteKey(write.location));
            }

            if (write.onComplete != null) {
                try {
                    write.onComplete.run();
                } catch (Throwable e) {
                    LOG.info("Add exception was raised while executing the run command for onComplete", e);
                }
            }

            write = write.getNext();
        }
    }

};