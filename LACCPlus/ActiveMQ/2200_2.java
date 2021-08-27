//,temp,TwoBrokerDurableForwardStaticRestartTest.java,133,146,temp,AMQ6366Test.java,128,140
//,3
public class xxx {
    @Override
    public void setUp() throws Exception {
        File dataDir = new File(IOHelper.getDefaultDataDirectory());
        LOG.info("Delete dataDir.." + dataDir.getCanonicalPath());
        org.apache.activemq.TestSupport.recursiveDelete(dataDir);
        super.setAutoFail(true);
        super.setUp();
        createBroker(new URI(
                "broker:(tcp://0.0.0.0:0)/BrokerA"));
        createBroker(new URI(
                "broker:(tcp://0.0.0.0:0)/BrokerB"));

    }

};