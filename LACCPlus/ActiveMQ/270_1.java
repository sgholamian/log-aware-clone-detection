//,temp,ErrorOnFutureSendTest.java,63,73,temp,MessageDatabaseSizeTest.java,78,88
//,3
public class xxx {
    protected void startBroker() throws Exception {
        broker = new BrokerService();
        broker.setDeleteAllMessagesOnStartup(true);
        broker.setPersistent(true);
        broker.setUseJmx(false);
        broker.setAdvisorySupport(false);
        broker.setDataDirectory(dataDir.getRoot().getAbsolutePath());
        adapter = (KahaDBPersistenceAdapter) broker.getPersistenceAdapter();
        broker.start();
        LOG.info("Starting broker..");
    }

};