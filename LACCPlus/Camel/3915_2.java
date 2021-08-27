//,temp,DefaultWebsocket.java,72,82,temp,DefaultWebsocket.java,62,70
//,3
public class xxx {
    @OnWebSocketMessage
    public void onMessage(String message) {
        LOG.debug("onMessage: {}", message);
        if (this.consumer != null) {
            this.consumer.sendMessage(this.connectionKey, message, getRemoteAddress());
        } else {
            LOG.debug("No consumer to handle message received: {}", message);
        }
    }

};