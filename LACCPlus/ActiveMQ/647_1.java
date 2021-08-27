//,temp,DemandForwardingBridgeSupport.java,1000,1007,temp,DemandForwardingBridgeSupport.java,918,925
//,3
public class xxx {
                @Override
                public void run() {
                    try {
                        localBroker.oneway(destInfo);
                    } catch (IOException e) {
                        LOG.warn("failed to deliver remove command for destination: {}", destInfo.getDestination(), e);
                    }
                }

};