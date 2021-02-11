//,temp,MessageBusBase.java,85,107,temp,MessageBusBase.java,63,83
//,3
public class xxx {
    @Override
    public void subscribe(String subject, MessageSubscriber subscriber) {
        assert (subject != null);
        assert (subscriber != null);
        if (_gate.enter()) {
            if (s_logger.isTraceEnabled()) {
                s_logger.trace("Enter gate in message bus subscribe");
            }
            try {
                SubscriptionNode current = locate(subject, null, true);
                assert (current != null);
                current.addSubscriber(subscriber);
            } finally {
                _gate.leave();
            }
        } else {
            synchronized (_pendingActions) {
                _pendingActions.add(new ActionRecord(ActionType.Subscribe, subject, subscriber));
            }
        }
    }

};