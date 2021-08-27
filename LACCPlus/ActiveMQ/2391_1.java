//,temp,TempStoreDataCleanupTest.java,114,166,temp,MemoryUsageCleanupTest.java,112,160
//,3
public class xxx {
    @Test
    public void testIt() throws Exception {

        int startPercentage = broker.getAdminView().getMemoryPercentUsage();
        LOG.info("MemoryUsage at test start = " + startPercentage);

        for (int i = 0; i < 2; i++) {
            LOG.info("Started the test iteration: " + i + " using queueName = " + queueName);
            queueName = QUEUE_NAME + i;
            final CountDownLatch latch = new CountDownLatch(11);

            pool.execute(new Runnable() {
                @Override
                public void run() {
                    receiveAndDiscard100messages(latch);
                }
            });

            for (int j = 0; j < 10; j++) {
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        send10000messages(latch);
                    }
                });
            }

            LOG.info("Waiting on the send / receive latch");
            latch.await(5, TimeUnit.MINUTES);
            LOG.info("Resumed");

            destroyQueue();
            TimeUnit.SECONDS.sleep(2);
        }

        LOG.info("MemoryUsage before awaiting temp store cleanup = " + broker.getAdminView().getMemoryPercentUsage());

        final PListStoreImpl pa = (PListStoreImpl) broker.getTempDataStore();
        assertTrue("only one journal file should be left: " + pa.getJournal().getFileMap().size(),
            Wait.waitFor(new Wait.Condition() {

                @Override
                public boolean isSatisified() throws Exception {
                    return pa.getJournal().getFileMap().size() == 1;
                }
            }, TimeUnit.MINUTES.toMillis(3))
        );

        int endPercentage = broker.getAdminView().getMemoryPercentUsage();
        LOG.info("MemoryUseage at test end = " + endPercentage);

        assertEquals(startPercentage, endPercentage);
    }

};