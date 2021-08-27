//,temp,AMQ3120Test.java,48,71,temp,AMQ4323Test.java,51,74
//,2
public class xxx {
    protected void startBroker(boolean delete) throws Exception {
        broker = new BrokerService();

        //Start with a clean directory
        kahaDbDir = new File(broker.getBrokerDataDirectory(), "KahaDB");
        deleteDir(kahaDbDir);

        broker.setSchedulerSupport(false);
        broker.setDeleteAllMessagesOnStartup(delete);
        broker.setPersistent(true);
        broker.setUseJmx(false);
        broker.addConnector("tcp://localhost:0");

        PolicyMap map = new PolicyMap();
        PolicyEntry entry = new PolicyEntry();
        entry.setUseCache(false);
        map.setDefaultEntry(entry);
        broker.setDestinationPolicy(map);

        configurePersistence(broker, delete);

        broker.start();
        LOG.info("Starting broker..");
    }

};