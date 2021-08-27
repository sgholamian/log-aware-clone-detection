//,temp,MissingDataFileTest.java,89,108,temp,VerifySteadyEnqueueRate.java,127,152
//,3
public class xxx {
    public void startBroker() throws Exception {
        broker = new BrokerService();
        broker.setDeleteAllMessagesOnStartup(true);
        broker.setPersistent(true);
        broker.setUseJmx(true);
        broker.addConnector("tcp://localhost:61616").setName("Default");

        SystemUsage systemUsage;
        systemUsage = new SystemUsage();
        systemUsage.getMemoryUsage().setLimit(10 * 1024 * 1024); // Just a few messags
        broker.setSystemUsage(systemUsage);

        KahaDBPersistenceAdapter kahaDBPersistenceAdapter = new KahaDBPersistenceAdapter();
        kahaDBPersistenceAdapter.setJournalMaxFileLength(16*1024);
        kahaDBPersistenceAdapter.setCleanupInterval(500);
        broker.setPersistenceAdapter(kahaDBPersistenceAdapter);

        broker.start();
        LOG.info("Starting broker..");
    }

};