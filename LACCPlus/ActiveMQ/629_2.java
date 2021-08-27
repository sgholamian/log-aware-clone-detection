//,temp,AmqpSession.java,114,127,temp,AmqpSession.java,103,112
//,3
public class xxx {
    @Override
    public void open() {
        LOG.debug("Session {} opened", getSessionId());

        getEndpoint().setContext(this);
        getEndpoint().setIncomingCapacity(Integer.MAX_VALUE);
        getEndpoint().open();

        connection.sendToActiveMQ(new SessionInfo(getSessionId()));
    }

};