//,temp,WebsocketHandler.java,66,73,temp,WebsocketStreamHandler.java,32,39
//,3
public class xxx {
    @Override
    public List<AtmosphereRequest> onMessage(WebSocket webSocket, String data) {
        LOG.debug("processing text message {}", data);
        String connectionKey = store.getConnectionKey(webSocket);
        consumer.sendMessage(connectionKey, data);
        LOG.debug("text message sent");
        return null;
    }

};