//,temp,FailoverStaticNetworkTest.java,246,295,temp,FailoverStaticNetworkTest.java,202,244
//,3
public class xxx {
    @Test
    public void testSendReceiveFailoverDuplex() throws Exception {
        final Vector<Throwable> errors = new Vector<Throwable>();
        final String dataDir = "target/data/shared";
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
                    errors.add(e);
                }
            }
        });
        executor.shutdown();

        HashMap<String, String> networkConnectorProps = new HashMap<String, String>();
        networkConnectorProps.put("duplex", "true");
        brokerB = createBroker("tcp", "62617", new String[]{"61617", "63617"}, networkConnectorProps);
        brokerB.start();

        doTestNetworkSendReceive(brokerA, brokerB);
        doTestNetworkSendReceive(brokerB, brokerA);

        LOG.info("stopping brokerA (master shared_broker)");
        brokerA.stop();
        brokerA.waitUntilStopped();

        // wait for slave to start
        brokerA1.waitUntilStarted();

        doTestNetworkSendReceive(brokerA1, brokerB);
        doTestNetworkSendReceive(brokerB, brokerA1);

        assertTrue("No unexpected exceptions " + errors, errors.isEmpty());
    }

};