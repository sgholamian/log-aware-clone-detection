//,temp,MessageBusBase.java,85,107,temp,MessageBusBase.java,63,83
//,3
public class xxx {
    @Override
    public void unsubscribe(String subject, MessageSubscriber subscriber) {
        if (_gate.enter()) {
            if (s_logger.isTraceEnabled()) {
                s_logger.trace("Enter gate in message bus unsubscribe");
            }
            try {
                if (subject != null) {
                    SubscriptionNode current = locate(subject, null, false);
                    if (current != null)
                        current.removeSubscriber(subscriber, false);
                } else {
                    _subscriberRoot.removeSubscriber(subscriber, true);
                }
            } finally {
                _gate.leave();
            }
        } else {
            synchronized (_pendingActions) {
                _pendingActions.add(new ActionRecord(ActionType.Unsubscribe, subject, subscriber));
            }
        }
    }

};