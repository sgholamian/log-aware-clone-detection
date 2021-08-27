//,temp,AMQ4636Test.java,65,72,temp,ActiveMQConnectionExecutorThreadCleanUpTest.java,51,62
//,3
public class xxx {
    @Before
    public void startBroker() throws Exception {
        broker = createBroker();
        broker.deleteAllMessages();
        broker.start();
        broker.waitUntilStarted();
        LOG.info("Broker started...");
    }

};