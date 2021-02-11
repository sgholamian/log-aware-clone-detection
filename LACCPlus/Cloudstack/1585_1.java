//,temp,SynchronousListener.java,71,79,temp,SshKeysDistriMonitor.java,60,66
//,3
public class xxx {
    @Override
    public synchronized boolean processDisconnect(long agentId, Status state) {
        if (s_logger.isTraceEnabled())
            s_logger.trace("Agent disconnected, agent id: " + agentId + ", state: " + state + ". Will notify waiters");

        _disconnected = true;
        notifyAll();
        return true;
    }

};