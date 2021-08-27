//,temp,MemoryWebSocketStore.java,84,91,temp,MemoryWebSocketStore.java,72,79
//,2
public class xxx {
    @Override
    public void removeWebSocket(String connectionKey) {
        Object obj = values.remove(connectionKey);
        if (obj != null) {
            keys.remove(obj);
        }
        LOG.debug("removed websocket {}", connectionKey);
    }

};