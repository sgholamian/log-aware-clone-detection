//,temp,ServerSessionPoolImpl.java,178,193,temp,ServerSessionPoolImpl.java,153,171
//,3
public class xxx {
    private ServerSessionImpl getExistingServerSession(boolean force) {
        ServerSessionImpl ss = null;
        if (idleSessions.size() > 0) {
            ss = idleSessions.remove(idleSessions.size() - 1);
        }
        if (ss != null) {
            activeSessions.add(ss);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Using idle session: " + ss);
            }
        } else if (force || activeSessions.size() >= maxSessions) {
            // If we are at the upper limit
            // then reuse the already created sessions..
            // This is going to queue up messages into a session for
            // processing.
            ss = getExistingActiveServerSession();
        }
        return ss;
    }

};