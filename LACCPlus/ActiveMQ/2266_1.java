//,temp,FailoverStaticNetworkTest.java,246,295,temp,FailoverStaticNetworkTest.java,202,244
//,3
public class xxx {
    @Test
    // master slave piggy in the middle setup
    public void testSendReceiveFailoverDuplexWithPIM() throws Exception {
        final String dataDir = "target/data/shared/pim";
        brokerA = createBroker("61617", dataDir);
        brokerA.start();

        final BrokerService slave = createBroker("63617", dataDir);
        brokerA1 = slave;
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    slave.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        executor.shutdown();

        HashMap<String, String> networkConnectorProps = new HashMap<String, String>();
        networkConnectorProps.put("duplex", "true");
        networkConnectorProps.put("networkTTL", "2");

        brokerB = createBroker("tcp", "62617", new String[]{"61617", "63617"}, networkConnectorProps);
        brokerB.start();

        assertTrue("all props applied", networkConnectorProps.isEmpty());
        networkConnectorProps.put("duplex", "true");
        networkConnectorProps.put("networkTTL", "2");

        brokerC = createBroker("tcp", "64617", new String[]{"61617", "63617"}, networkConnectorProps);
        brokerC.start();
        assertTrue("all props applied a second time", networkConnectorProps.isEmpty());

        doTestNetworkSendReceive(brokerC, brokerB);
        doTestNetworkSendReceive(brokerB, brokerC);

        LOG.info("stopping brokerA (master shared_broker)");
        brokerA.stop();
        brokerA.waitUntilStopped();

        doTestNetworkSendReceive(brokerC, brokerB);
        doTestNetworkSendReceive(brokerB, brokerC);

        brokerC.stop();
        brokerC.waitUntilStopped();
    }

};