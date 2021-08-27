//,temp,AbstractKahaDBStore.java,114,137,temp,MessageDatabase.java,490,510
//,3
public class xxx {
    public void load() throws IOException {
        this.indexLock.writeLock().lock();
        try {
            IOHelper.mkdirs(directory);
            if (deleteAllMessages) {
                getJournal().setCheckForCorruptionOnStartup(false);
                getJournal().start();
                getJournal().delete();
                getJournal().close();
                journal = null;
                getPageFile().delete();
                LOG.info("Persistence store purged.");
                deleteAllMessages = false;
            }

            open();
            store(new KahaTraceCommand().setMessage("LOADED " + new Date()));
        } finally {
            this.indexLock.writeLock().unlock();
        }
    }

};