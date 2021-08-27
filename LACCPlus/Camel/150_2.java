//,temp,FileIdempotentRepositoryReadLockStrategy.java,120,145,temp,FileIdempotentRepositoryReadLockStrategy.java,93,118
//,2
public class xxx {
    @Override
    public void releaseExclusiveReadLockOnRollback(
            GenericFileOperations<File> operations, GenericFile<File> file, Exchange exchange)
            throws Exception {
        String key = asKey(file);
        Runnable r = () -> {
            if (removeOnRollback) {
                idempotentRepository.remove(exchange, key);
            } else {
                // okay we should not remove then confirm it instead
                idempotentRepository.confirm(exchange, key);
            }
        };

        if (readLockIdempotentReleaseDelay > 0 && readLockIdempotentReleaseExecutorService != null) {
            LOG.debug("Scheduling readlock release task to run asynchronous delayed after {} millis",
                    readLockIdempotentReleaseDelay);
            readLockIdempotentReleaseExecutorService.schedule(r, readLockIdempotentReleaseDelay, TimeUnit.MILLISECONDS);
        } else if (readLockIdempotentReleaseDelay > 0) {
            LOG.debug("Delaying readlock release task {} millis", readLockIdempotentReleaseDelay);
            Thread.sleep(readLockIdempotentReleaseDelay);
            r.run();
        } else {
            r.run();
        }
    }

};