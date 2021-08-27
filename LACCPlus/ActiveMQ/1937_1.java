//,temp,FailoverStaticNetworkTest.java,374,393,temp,FailoverStaticNetworkTest.java,344,362
//,2
public class xxx {
            @Override
            public void run() {
                try {
                    while (!done.get()) {
                        brokerA1 = createBroker("tcp", "61611", null);
                        brokerA1.setBrokerName("Pair");
                        // so they can coexist in local jmx we set the object name b/c the brokername identifies the shared store
                        brokerA1.setBrokerObjectName(new ObjectName(brokerA.getManagementContext().getJmxDomainName() + ":" + "brokerName="
                            + JMXSupport.encodeObjectNamePart("A1") + "," + "Type=Broker"));
                        ((KahaDBPersistenceAdapter)brokerA1.getPersistenceAdapter()).getLocker().setLockAcquireSleepInterval(1000);
                        brokerA1.start();
                        brokerA1.waitUntilStopped();

                        // restart after peer taken over
                        brokerA.waitUntilStarted();
                    }
                } catch (Exception ignored) {
                    LOG.info("A1 create/start, unexpected: " + ignored, ignored);
                }
            }

};