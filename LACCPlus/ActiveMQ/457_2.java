//,temp,PListStoreImpl.java,289,347,temp,LegacyJobSchedulerStoreImpl.java,236,283
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        if (this.directory == null) {
            this.directory = new File(IOHelper.getDefaultDataDirectory() + File.pathSeparator + "delayedDB");
        }
        IOHelper.mkdirs(this.directory);
        lock();
        this.journal = new Journal();
        this.journal.setDirectory(directory);
        this.journal.setMaxFileLength(getJournalMaxFileLength());
        this.journal.setWriteBatchSize(getJournalMaxWriteBatchSize());
        this.journal.setSizeAccumulator(this.journalSize);
        this.journal.start();
        this.pageFile = new PageFile(directory, "scheduleDB");
        this.pageFile.setWriteBatchSize(1);
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
                metaData.loadScheduler(tx, schedulers);
                for (LegacyJobSchedulerImpl js : schedulers.values()) {
                    try {
                        js.start();
                    } catch (Exception e) {
                        LegacyJobSchedulerStoreImpl.LOG.error("Failed to load " + js.getName(), e);
                    }
                }
            }
        });

        this.pageFile.flush();
        LOG.info(this + " started");
    }

};