//,temp,AbstractKahaDBStore.java,114,137,temp,MessageDatabase.java,490,510
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        this.indexLock.writeLock().lock();
        if (getDirectory() == null) {
            setDirectory(getDefaultDataDirectory());
        }
        IOHelper.mkdirs(getDirectory());
        try {
            if (isPurgeStoreOnStartup()) {
                getJournal().start();
                getJournal().delete();
                getJournal().close();
                journal = null;
                getPageFile().delete();
                LOG.info("{} Persistence store purged.", this);
                setPurgeStoreOnStartup(false);
            }

            load();
            store(new KahaTraceCommand().setMessage("LOADED " + new Date()));
        } finally {
            this.indexLock.writeLock().unlock();
        }
    }

};