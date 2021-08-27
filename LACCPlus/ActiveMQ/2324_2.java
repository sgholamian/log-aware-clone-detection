//,temp,LegacyJobSchedulerStoreImpl.java,254,278,temp,JobSchedulerStoreImpl.java,272,297
//,3
public class xxx {
                @Override
                public void execute(Transaction tx) throws IOException {
                    if (pageFile.getPageCount() == 0) {
                        Page<JobSchedulerKahaDBMetaData> page = tx.allocate();
                        assert page.getPageId() == 0;
                        page.set(metaData);
                        metaData.setPage(page);
                        metaData.setState(KahaDBMetaData.CLOSED_STATE);
                        metaData.initialize(tx);
                        tx.store(metaData.getPage(), metaDataMarshaller, true);
                    } else {
                        Page<JobSchedulerKahaDBMetaData> page = null;
                        page = tx.load(0, metaDataMarshaller);
                        metaData = page.get();
                        metaData.setPage(page);
                    }
                    metaData.load(tx);
                    metaData.loadScheduler(tx, schedulers);
                    for (JobSchedulerImpl js : schedulers.values()) {
                        try {
                            js.start();
                        } catch (Exception e) {
                            JobSchedulerStoreImpl.LOG.error("Failed to load " + js.getName(), e);
                        }
                    }
                }

};