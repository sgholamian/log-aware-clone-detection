//,temp,CompressionOverNetworkTest.java,235,253,temp,SimpleNetworkTest.java,172,194
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                Object[] bridges = brokerService.getNetworkConnectors().get(0).bridges.values().toArray();
                if (bridges.length > 0) {
                    LOG.info(brokerService + " bridges "  + Arrays.toString(bridges));
                    DemandForwardingBridgeSupport demandForwardingBridgeSupport = (DemandForwardingBridgeSupport) bridges[0];
                    ConcurrentMap<ConsumerId, DemandSubscription> forwardingBridges = demandForwardingBridgeSupport.getLocalSubscriptionMap();
                    LOG.info(brokerService + " bridge "  + demandForwardingBridgeSupport + ", localSubs: " + forwardingBridges);
                    if (!forwardingBridges.isEmpty()) {
                        for (DemandSubscription demandSubscription : forwardingBridges.values()) {
                            if (demandSubscription.getLocalInfo().getDestination().equals(destination)) {
                                LOG.info(brokerService + " DemandSubscription "  + demandSubscription + ", size: " + demandSubscription.size());
                                return demandSubscription.size() >= min;
                            }
                        }
                    }
                }
                return false;
            }

};