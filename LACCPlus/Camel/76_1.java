//,temp,MemoryWebSocketStore.java,84,91,temp,MemoryWebSocketStore.java,72,79
//,2
public class xxx {
    @Override
    public void removeWebSocket(WebSocket websocket) {
        Object obj = keys.remove(websocket);
        if (obj != null) {
            values.remove(obj);
        }
        LOG.debug("removed websocket {}", websocket);
    }

};