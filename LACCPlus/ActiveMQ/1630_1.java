//,temp,DurableSubSelectorDelayTest.java,54,88,temp,DurableSubProcessMultiRestartTest.java,84,130
//,3
public class xxx {
    @Test
    public void testProcess() throws Exception {

        MsgProducer msgProducer = new MsgProducer();
        msgProducer.start();

        DurableSubscriber subscribers[] = new DurableSubscriber[10];

        for (int i = 0; i < subscribers.length; i++) {
            subscribers[i] = new DurableSubscriber(i);
            subscribers[i].process();
        }

        // wait for server to finish
        msgProducer.join();

        for (int j = 0; j < subscribers.length; j++) {
            LOG.info("Unsubscribing subscriber " + subscribers[j]);
            subscribers[j].unsubscribe();
        }

        // allow the clean up thread time to run
        TimeUnit.MINUTES.sleep(2);

        final KahaDBPersistenceAdapter pa = (KahaDBPersistenceAdapter) broker.getPersistenceAdapter();
        assertTrue("less than two journal file should be left, was: " + pa.getStore().getJournal().getFileMap().size(), Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return pa.getStore().getJournal().getFileMap().size() <= 2;
            }
        }, TimeUnit.MINUTES.toMillis(2)));

        LOG.info("DONE.");
    }

};