//,temp,AMQ2584Test.java,79,131,temp,AMQ2870Test.java,77,131
//,3
public class xxx {
    @Test(timeout = 300000)
    public void testSize() throws Exception {
        openConsumer();

        assertEquals(0, broker.getAdminView().getStorePercentUsage());

        for (int i = 0; i < 5000; i++) {
            sendMessage(false);
        }

        final BrokerView brokerView = broker.getAdminView();

        // wait for reclaim
        assertTrue("in range with consumer",
                Wait.waitFor(new Wait.Condition() {
                    @Override
                    public boolean isSatisified() throws Exception {
                        // usage percent updated only on send check for isFull so once
                        // sends complete it is no longer updated till next send via a call to isFull
                        // this is optimal as it is only used to block producers
                        broker.getSystemUsage().getStoreUsage().isFull();
                        LOG.info("store percent usage: "+brokerView.getStorePercentUsage());
                        return broker.getAdminView().getStorePercentUsage() < minPercentUsageForStore;
                    }
                }));

        closeConsumer();

        assertTrue("in range with closed consumer",
                Wait.waitFor(new Wait.Condition() {
                    @Override
                    public boolean isSatisified() throws Exception {
                        broker.getSystemUsage().getStoreUsage().isFull();
                        LOG.info("store precent usage: "+brokerView.getStorePercentUsage());
                        return broker.getAdminView().getStorePercentUsage() < minPercentUsageForStore;
                    }
                }));

        for (int i = 0; i < 5000; i++) {
            sendMessage(false);
        }

        // What if i drop the subscription?
        broker.getAdminView().destroyDurableSubscriber("cliID", "subName");

        assertTrue("in range after send with consumer",
                Wait.waitFor(new Wait.Condition() {
                    @Override
                    public boolean isSatisified() throws Exception {
                        broker.getSystemUsage().getStoreUsage().isFull();
                        LOG.info("store precent usage: "+brokerView.getStorePercentUsage());
                        return broker.getAdminView().getStorePercentUsage() < minPercentUsageForStore;
                    }
                }));
    }

};