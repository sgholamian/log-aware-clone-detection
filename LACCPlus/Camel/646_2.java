//,temp,WebsocketHandler.java,66,73,temp,WebsocketStreamHandler.java,32,39
//,3
public class xxx {
    @Override
    public List<AtmosphereRequest> onTextStream(WebSocket webSocket, Reader data) {
        LOG.debug("processing reader message {}", data);
        String connectionKey = store.getConnectionKey(webSocket);
        consumer.sendMessage(connectionKey, data);
        LOG.debug("reader message sent");
        return null;
    }

};