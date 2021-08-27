//,temp,DemandForwardingBridgeSupport.java,242,251,temp,DemandForwardingBridgeSupport.java,222,231
//,2
public class xxx {
                @Override
                public void onException(IOException error) {
                    if (!futureRemoteBrokerInfo.isDone()) {
                        LOG.info("Error with pending remote brokerInfo on: {} ({})", remoteBroker, error.getMessage());
                        LOG.debug("Peer error: ", error);
                        futureRemoteBrokerInfo.cancel(true);
                        return;
                    }
                    serviceRemoteException(error);
                }

};