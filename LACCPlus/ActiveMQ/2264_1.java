//,temp,DemandForwardingBridgeSupport.java,1411,1438,temp,ConduitBridge.java,59,102
//,3
public class xxx {
    protected void addConsumerInfo(final ConsumerInfo consumerInfo) throws IOException {
        ConsumerInfo info = consumerInfo.copy();
        addRemoteBrokerToBrokerPath(info);
        DemandSubscription sub = createDemandSubscription(info);
        if (sub != null) {
            if (duplicateSuppressionIsRequired(sub)) {
                undoMapRegistration(sub);
            } else {
                if (consumerInfo.isDurable()) {
                    //Handle the demand generated by proxy network subscriptions
                    //The broker path is case is normal
                    if (isProxyNSConsumerBrokerPath(sub.getRemoteInfo()) &&
                            info.getSubscriptionName() != null && info.getSubscriptionName().startsWith(DURABLE_SUB_PREFIX)) {
                        final BrokerId[] path = info.getBrokerPath();
                        addProxyNetworkSubscriptionBrokerPath(sub, path, consumerInfo.getSubscriptionName());
                    //This is the durable sync case on broker restart
                    } else if (isProxyNSConsumerClientId(sub.getRemoteInfo().getClientId()) &&
                            isProxyBridgeSubscription(info.getClientId(), info.getSubscriptionName())) {
                		addProxyNetworkSubscriptionClientId(sub, sub.getRemoteInfo().getClientId(), consumerInfo.getSubscriptionName());
                	} else {
            			sub.getDurableRemoteSubs().add(new SubscriptionInfo(sub.getRemoteInfo().getClientId(), consumerInfo.getSubscriptionName()));
            		}
                }
                addSubscription(sub);
                LOG.debug("{} new demand subscription: {}", configuration.getBrokerName(), sub);
            }
        }
    }

};