//,temp,AbstractKahaDBStore.java,478,521,temp,MessageDatabase.java,1142,1182
//,3
public class xxx {
    public Location store(JournalCommand<?> command, boolean sync, Runnable before, Runnable after, Runnable onJournalStoreComplete) throws IOException {
        try {

            if (before != null) {
                before.run();
            }

            ByteSequence sequence = toByteSequence(command);
            Location location;
            checkpointLock.readLock().lock();
            try {

                long start = System.currentTimeMillis();
                location = onJournalStoreComplete == null ? journal.write(sequence, sync) :
                                                            journal.write(sequence, onJournalStoreComplete);
                long start2 = System.currentTimeMillis();

                process(command, location);

                long end = System.currentTimeMillis();
                if (LOG_SLOW_ACCESS_TIME > 0 && end - start > LOG_SLOW_ACCESS_TIME) {
                    LOG.info("Slow KahaDB access: Journal append took: {} ms, Index update took {} ms",
                             (start2-start), (end-start2));
                }
            } finally {
                checkpointLock.readLock().unlock();
            }

            if (after != null) {
                after.run();
            }

            if (checkpointThread != null && !checkpointThread.isAlive()) {
                startCheckpoint();
            }
            return location;
        } catch (IOException ioe) {
            LOG.error("KahaDB failed to store to Journal", ioe);
            if (brokerService != null) {
                brokerService.handleIOException(ioe);
            }
            throw ioe;
        }
    }

};