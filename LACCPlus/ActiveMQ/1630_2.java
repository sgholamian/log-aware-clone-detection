//,temp,DurableSubSelectorDelayTest.java,54,88,temp,DurableSubProcessMultiRestartTest.java,84,130
//,3
public class xxx {
    @Test
    public void testProcess() throws Exception {

        DurableSubscriber durableSubscriber = new DurableSubscriber(SUBSCRIPTION_ID);
        MsgProducer msgProducer = new MsgProducer();

        try {
            // register the subscription & start messages
            durableSubscriber.start();
            msgProducer.start();

            long endTime = System.currentTimeMillis() + RUNTIME;

            while (endTime > System.currentTimeMillis()) {
                Thread.sleep(10000);
                restartBroker();
            }
        } catch (Throwable e) {
            exit("ProcessTest.testProcess failed.", e);
        }

        // wait for threads to finish
        try {
            msgProducer.join();
            durableSubscriber.join();
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        }

        // restart broker one last time
        restartBroker();

        assertTrue("no exceptions: " + exceptions, exceptions.isEmpty());

        final KahaDBPersistenceAdapter pa = (KahaDBPersistenceAdapter) broker.getPersistenceAdapter();
        assertTrue("only less than two journal files should be left: " + pa.getStore().getJournal().getFileMap().size(),
            Wait.waitFor(new Wait.Condition() {

                @Override
                public boolean isSatisified() throws Exception {
                    return pa.getStore().getJournal().getFileMap().size() <= 2;
                }
            }, TimeUnit.MINUTES.toMillis(3))
        );

        LOG.info("DONE.");
    }

};