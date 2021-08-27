//,temp,JobSchedulerStoreImpl.java,169,204,temp,MessageDatabase.java,459,488
//,3
public class xxx {
    @Override
    public void load() throws IOException {
        if (opened.compareAndSet(false, true)) {
            getJournal().start();
            try {
                loadPageFile();
            } catch (UnknownStoreVersionException ex) {
                LOG.info("Can't start until store update is performed.");
                upgradeFromLegacy();
                // Restart with the updated store
                getJournal().start();
                loadPageFile();
                LOG.info("Update from legacy Scheduler store completed successfully.");
            } catch (Throwable t) {
                LOG.warn("Index corrupted. Recovering the index through journal replay. Cause: {}", t.toString());
                LOG.debug("Index load failure", t);

                // try to recover index
                try {
                    pageFile.unload();
                } catch (Exception ignore) {
                }
                if (isArchiveCorruptedIndex()) {
                    pageFile.archive();
                } else {
                    pageFile.delete();
                }
                metaData = new JobSchedulerKahaDBMetaData(this);
                pageFile = null;
                loadPageFile();
            }
            startCheckpoint();
            recover();
        }
        LOG.info("{} started.", this);
    }

};