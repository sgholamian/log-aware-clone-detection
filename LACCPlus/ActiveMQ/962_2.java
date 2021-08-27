//,temp,AbstractKahaDBStore.java,478,521,temp,MessageDatabase.java,1142,1182
//,3
public class xxx {
    public Location store(JournalCommand<?> data, boolean sync, IndexAware before, Runnable after, Runnable onJournalStoreComplete) throws IOException {
        try {
            ByteSequence sequence = toByteSequence(data);
            Location location;

            checkpointLock.readLock().lock();
            try {

                long start = System.currentTimeMillis();
                location = onJournalStoreComplete == null ? journal.write(sequence, sync) : journal.write(sequence, onJournalStoreComplete) ;
                long start2 = System.currentTimeMillis();
                //Track the last async update so we know if we need to sync at the next checkpoint
                if (!sync && journal.isJournalDiskSyncPeriodic()) {
                    lastAsyncJournalUpdate.set(location);
                }
                process(data, location, before);

                long end = System.currentTimeMillis();
                if (LOG_SLOW_ACCESS_TIME > 0 && end - start > LOG_SLOW_ACCESS_TIME) {
                    if (LOG.isInfoEnabled()) {
                        LOG.info("Slow KahaDB access: Journal append took: "+(start2-start)+" ms, Index update took "+(end-start2)+" ms");
                    }
                }

                persistenceAdapterStatistics.addWriteTime(end - start);

            } finally {
                checkpointLock.readLock().unlock();
            }

            if (after != null) {
                after.run();
            }

            return location;
        } catch (IOException ioe) {
            LOG.error("KahaDB failed to store to Journal, command of type: " + data.type(), ioe);
            brokerService.handleIOException(ioe);
            throw ioe;
        }
    }

};