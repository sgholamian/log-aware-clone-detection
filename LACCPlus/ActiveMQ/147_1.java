//,temp,ServerSessionPoolImpl.java,178,193,temp,ServerSessionPoolImpl.java,153,171
//,3
public class xxx {
    private ServerSessionImpl getExistingActiveServerSession() {
        ServerSessionImpl ss = null;
        if (!activeSessions.isEmpty()) {
            if (activeSessions.size() > 1) {
                // round robin
                ss = activeSessions.remove(0);
                activeSessions.add(ss);
            } else {
                ss = activeSessions.get(0);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Reusing an active session: " + ss);
        }
        return ss;
    }

};