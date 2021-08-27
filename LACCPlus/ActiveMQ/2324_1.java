//,temp,LegacyJobSchedulerStoreImpl.java,254,278,temp,JobSchedulerStoreImpl.java,272,297
//,3
public class xxx {
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

};