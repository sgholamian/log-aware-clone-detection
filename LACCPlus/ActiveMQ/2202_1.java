//,temp,PrefetchSubscription.java,752,771,temp,PrefetchSubscription.java,727,742
//,3
public class xxx {
    protected void onDispatch(final MessageReference node, final Message message) {
        Destination nodeDest = (Destination) node.getRegionDestination();
        if (nodeDest != null) {
            if (node != QueueMessageReference.NULL_MESSAGE) {
                nodeDest.getDestinationStatistics().getDispatched().increment();
                incrementPrefetchCounter(node);
                LOG.trace("{} dispatched: {} - {}, dispatched: {}, inflight: {}",
                        info.getConsumerId(), message.getMessageId(), message.getDestination(),
                        getSubscriptionStatistics().getDispatched().getCount(), dispatched.size());
            }
        }

        if (info.isDispatchAsync()) {
            try {
                dispatchPending();
            } catch (IOException e) {
                context.getConnection().serviceExceptionAsync(e);
            }
        }
    }

};