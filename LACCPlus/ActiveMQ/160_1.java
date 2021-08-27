//,temp,JournalCorruptionIndexRecoveryTest.java,90,105,temp,JournalCorruptionExceptionTest.java,86,108
//,3
public class xxx {
    private void doStartBroker(boolean delete) throws Exception {
        broker = new BrokerService();
        broker.setDeleteAllMessagesOnStartup(delete);
        broker.setPersistent(true);
        broker.setUseJmx(true);
        broker.setDataDirectory(KAHADB_DIRECTORY);
        broker.addConnector("tcp://localhost:0");

        configurePersistence(broker);

        connectionUri = "vm://localhost?create=false";
        cf = new ActiveMQConnectionFactory(connectionUri);

        broker.start();
        LOG.info("Starting broker..");
    }

};