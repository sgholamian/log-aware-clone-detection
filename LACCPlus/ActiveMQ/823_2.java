//,temp,AbstractKahaDBStore.java,737,753,temp,MessageDatabase.java,1093,1111
//,3
public class xxx {
    protected void checkpointCleanup(final boolean cleanup) throws IOException {
        long start;
        this.indexLock.writeLock().lock();
        try {
            start = System.currentTimeMillis();
            if( !opened.get() ) {
                return;
            }
        } finally {
            this.indexLock.writeLock().unlock();
        }
        checkpointUpdate(cleanup);
        long end = System.currentTimeMillis();
        if (LOG_SLOW_ACCESS_TIME > 0 && end - start > LOG_SLOW_ACCESS_TIME) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Slow KahaDB access: cleanup took " + (end - start));
            }
        }
    }

};