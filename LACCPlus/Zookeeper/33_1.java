//,temp,LearnerSessionTracker.java,113,142,temp,LeaderSessionTracker.java,104,130
//,3
public class xxx {
    public synchronized boolean commitSession(
            long sessionId, int sessionTimeout) {
        boolean added =
            globalSessionsWithTimeouts.put(sessionId, sessionTimeout) == null;

        if (added) {
            // Only do extra logging so we know what kind of session this is
            // if we're supporting both kinds of sessions
            LOG.info("Committing global session 0x" + Long.toHexString(sessionId));
        }

        // If the session moved before the session upgrade finished, it's
        // possible that the session will be added to the local session
        // again. Need to double check and remove it from local session
        // tracker when the global session is quorum committed, otherwise the
        // local session might be tracked both locally and on leader.
        //
        // This cannot totally avoid the local session being upgraded again
        // because there is still race condition between create another upgrade
        // request and process the createSession commit, and there is no way
        // to know there is a on flying createSession request because it might
        // be upgraded by other server which owns the session before move.
        if (localSessionsEnabled) {
            removeLocalSession(sessionId);
            finishedUpgrading(sessionId);
        }

        touchTable.get().put(sessionId, sessionTimeout);
        return added;
    }

};