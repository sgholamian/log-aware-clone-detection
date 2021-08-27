//,temp,FtpChangedExclusiveReadLockStrategy.java,152,161,temp,FileLockExclusiveReadLockStrategy.java,181,190
//,2
public class xxx {
    private boolean sleep() {
        LOG.trace("Exclusive read lock not granted. Sleeping for {} millis.", checkInterval);
        try {
            Thread.sleep(checkInterval);
            return false;
        } catch (InterruptedException e) {
            LOG.debug("Sleep interrupted while waiting for exclusive read lock, so breaking out");
            return true;
        }
    }

};