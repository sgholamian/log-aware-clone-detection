//,temp,BoxEventsManager.java,56,77,temp,BoxCollaborationsManager.java,80,101
//,3
public class xxx {
    public void listen(EventListener listener, Long startingPosition) {
        try {
            if (listener == null) {
                LOG.debug("Parameter 'listener' is null: will not listen for events");
                return;
            }
            LOG.debug("Listening for events with listener={} at startingPosition={}", listener, startingPosition);

            if (startingPosition != null) {
                eventStream = new EventStream(boxConnection, startingPosition);
            } else {
                eventStream = new EventStream(boxConnection);
            }

            eventStream.addListener(listener);

            eventStream.start();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};