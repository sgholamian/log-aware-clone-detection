//,temp,MessageBusBase.java,128,144,temp,MessageBusBase.java,109,126
//,3
public class xxx {
    @Override
    public void clearAll() {
        if (_gate.enter()) {
            if (s_logger.isTraceEnabled()) {
                s_logger.trace("Enter gate in message bus clearAll");
            }
            try {
                _subscriberRoot.clearAll();
                doPrune();
            } finally {
                _gate.leave();
            }
        } else {
            synchronized (_pendingActions) {
                _pendingActions.add(new ActionRecord(ActionType.ClearAll, null, null));
            }
        }
    }

};