//,temp,FileIdempotentChangedRepositoryReadLockStrategy.java,146,177,temp,FileIdempotentChangedRepositoryReadLockStrategy.java,113,144
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

            try {
                changed.releaseExclusiveReadLockOnRollback(operations, file, exchange);
            } catch (Exception e) {
                LOG.warn("Error during releasing exclusive readlock on rollback. This exception is ignored.", e);
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