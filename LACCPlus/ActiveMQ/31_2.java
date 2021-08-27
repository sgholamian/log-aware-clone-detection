//,temp,AMQ4636Test.java,65,72,temp,ActiveMQConnectionExecutorThreadCleanUpTest.java,51,62
//,3
public class xxx {
    @Before
    public void setUp() throws Exception {
        LOG.info("Configuring broker programmatically.");
        broker = new BrokerService();
        broker.setPersistent(false);

        // explicitly limiting to 0 connections so that test is unable
        // to connect
        broker.addConnector("tcp://localhost:0?maximumConnections=0");
        broker.start();
        broker.waitUntilStarted(5000);
    }

};