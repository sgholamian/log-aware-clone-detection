//,temp,JobSchedulerStoreImpl.java,169,204,temp,MessageDatabase.java,459,488
//,3
public class xxx {
    public void open() throws IOException {
        if( opened.compareAndSet(false, true) ) {
            getJournal().start();
            try {
                loadPageFile();
            } catch (Throwable t) {
                LOG.warn("Index corrupted. Recovering the index through journal replay. Cause:" + t);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Index load failure", t);
                }
                // try to recover index
                try {
                    pageFile.unload();
                } catch (Exception ignore) {}
                if (archiveCorruptedIndex) {
                    pageFile.archive();
                } else {
                    pageFile.delete();
                }
                metadata = createMetadata();
                //The metadata was recreated after a detect corruption so we need to
                //reconfigure anything that was configured on the old metadata on startup
                configureMetadata();
                pageFile = null;
                loadPageFile();
            }
            recover();
            startCheckpoint();
        }
    }

};