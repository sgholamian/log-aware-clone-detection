//,temp,DemandForwardingBridgeSupport.java,242,251,temp,DemandForwardingBridgeSupport.java,222,231
//,2
public class xxx {
                @Override
                public void onException(IOException error) {
                    if (!futureLocalBrokerInfo.isDone()) {
                        LOG.info("Error with pending local brokerInfo on: {} ({})", localBroker, error.getMessage());
                        LOG.debug("Peer error: ", error);
                        futureLocalBrokerInfo.cancel(true);
                        return;
                    }
                    serviceLocalException(error);
                }

};