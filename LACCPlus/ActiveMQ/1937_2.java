//,temp,FailoverStaticNetworkTest.java,374,393,temp,FailoverStaticNetworkTest.java,344,362
//,2
public class xxx {
            @Override
            public void run() {
                try {
                    while (!done.get()) {
                        brokerA = createBroker("tcp", "61610", null);
                        brokerA.setBrokerName("Pair");
                        brokerA.setBrokerObjectName(new ObjectName(brokerA.getManagementContext().getJmxDomainName() + ":" + "brokerName="
                                + JMXSupport.encodeObjectNamePart("A") + "," + "Type=Broker"));
                        ((KahaDBPersistenceAdapter)brokerA.getPersistenceAdapter()).getLocker().setLockAcquireSleepInterval(1000);
                        brokerA.start();
                        brokerA.waitUntilStopped();

                        // restart after peer taken over
                        brokerA1.waitUntilStarted();
                    }
                } catch (Exception ignored) {
                    LOG.info("A create/start, unexpected: " + ignored, ignored);
                }
            }

};