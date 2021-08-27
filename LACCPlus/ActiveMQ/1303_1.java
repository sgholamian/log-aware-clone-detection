//,temp,AMQ2584Test.java,176,189,temp,AMQ2870Test.java,168,181
//,2
public class xxx {
    private void startBroker(boolean deleteMessages) throws Exception {
        broker = new BrokerService();
        broker.setAdvisorySupport(false);
        broker.setBrokerName("testStoreSize");

        if (deleteMessages) {
            broker.setDeleteAllMessagesOnStartup(true);
        }
        LOG.info("Starting broker with persistenceAdapterChoice " + persistenceAdapterChoice.toString());
        setPersistenceAdapter(broker, persistenceAdapterChoice);
        configurePersistenceAdapter(broker.getPersistenceAdapter());
        broker.getSystemUsage().getStoreUsage().setLimit(200 * 1000 * 1000);
        broker.start();
    }

};