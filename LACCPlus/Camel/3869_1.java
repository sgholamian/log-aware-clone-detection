//,temp,CamelServerEndpoint.java,61,73,temp,ClientSessions.java,109,125
//,3
public class xxx {
    @Override
    public void onError(final Session session, final Throwable throwable) {
        synchronized (session) {
            if (session.isOpen()) {
                try {
                    session.close(new CloseReason(CloseReason.CloseCodes.CLOSED_ABNORMALLY, "an exception occurred"));
                } catch (final IOException e) {
                    log.debug("Error closing session #{}", session.getId(), e);
                }
            }
        }
        log.debug("Error on session #{}", session.getId(), throwable);
    }

};