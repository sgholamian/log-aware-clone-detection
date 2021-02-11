//,temp,MessageBusBase.java,128,144,temp,MessageBusBase.java,109,126
//,3
public class xxx {
    @Override
    public void prune() {
        if (_gate.enter()) {
            if (s_logger.isTraceEnabled()) {
                s_logger.trace("Enter gate in message bus prune");
            }
            try {
                doPrune();
            } finally {
                _gate.leave();
            }
        } else {
            synchronized (_pendingActions) {
                _pendingActions.add(new ActionRecord(ActionType.Prune, null, null));
            }
        }
    }

};