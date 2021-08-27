//,temp,PrefetchSubscription.java,752,771,temp,PrefetchSubscription.java,727,742
//,3
public class xxx {
                @Override
                public void onFailure() {
                    Destination nodeDest = (Destination) node.getRegionDestination();
                    if (nodeDest != null) {
                        if (node != QueueMessageReference.NULL_MESSAGE) {
                            nodeDest.getDestinationStatistics().getDispatched().increment();
                            incrementPrefetchCounter(node);
                            LOG.trace("{} failed to dispatch: {} - {}, dispatched: {}, inflight: {}",
                                    info.getConsumerId(), message.getMessageId(), message.getDestination(),
                                    getSubscriptionStatistics().getDispatched().getCount(), dispatched.size());
                        }
                    }
                    if (node instanceof QueueMessageReference) {
                        ((QueueMessageReference) node).unlock();
                    }
                }

};