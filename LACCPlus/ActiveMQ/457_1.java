//,temp,PListStoreImpl.java,289,347,temp,LegacyJobSchedulerStoreImpl.java,236,283
//,3
public class xxx {
    protected synchronized void intialize() throws Exception {
        if (isStarted()) {
            if (this.initialized == false) {
                if (this.directory == null) {
                    this.directory = getDefaultDirectory();
                }
                IOHelper.mkdirs(this.directory);
                IOHelper.deleteChildren(this.directory);
                if (this.indexDirectory != null) {
                    IOHelper.mkdirs(this.indexDirectory);
                    IOHelper.deleteChildren(this.indexDirectory);
                }
                lock();
                this.journal = new Journal();
                this.journal.setDirectory(directory);
                this.journal.setMaxFileLength(getJournalMaxFileLength());
                this.journal.setWriteBatchSize(getJournalMaxWriteBatchSize());
                this.journal.start();
                this.pageFile = new PageFile(getIndexDirectory(), "tmpDB");
                this.pageFile.setEnablePageCaching(getIndexEnablePageCaching());
                this.pageFile.setPageSize(getIndexPageSize());
                this.pageFile.setWriteBatchSize(getIndexWriteBatchSize());
                this.pageFile.setPageCacheSize(getIndexCacheSize());
                this.pageFile.load();

                this.pageFile.tx().execute(new Transaction.Closure<IOException>() {
                    @Override
                    public void execute(Transaction tx) throws IOException {
                        if (pageFile.getPageCount() == 0) {
                            Page<MetaData> page = tx.allocate();
                            assert page.getPageId() == 0;
                            page.set(metaData);
                            metaData.page = page;
                            metaData.createIndexes(tx);
                            tx.store(metaData.page, metaDataMarshaller, true);

                        } else {
                            Page<MetaData> page = tx.load(0, metaDataMarshaller);
                            metaData = page.get();
                            metaData.page = page;
                        }
                        metaData.load(tx);
                        metaData.loadLists(tx, persistentLists);
                    }
                });
                this.pageFile.flush();

                if (cleanupInterval > 0) {
                    if (scheduler == null) {
                        scheduler = new Scheduler(PListStoreImpl.class.getSimpleName());
                        scheduler.start();
                    }
                    scheduler.executePeriodically(this, cleanupInterval);
                }
                this.initialized = true;
                LOG.info(this + " initialized");
            }
        }
    }

};