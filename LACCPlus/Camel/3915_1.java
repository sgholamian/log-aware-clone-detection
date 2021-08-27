//,temp,DefaultWebsocket.java,72,82,temp,DefaultWebsocket.java,62,70
//,3
public class xxx {
    @OnWebSocketMessage
    public void onMessage(byte[] data, int offset, int length) {
        LOG.debug("onMessage: byte[]");
        if (this.consumer != null) {
            byte[] message = new byte[length];
            System.arraycopy(data, offset, message, 0, length);
            this.consumer.sendMessage(this.connectionKey, message, getRemoteAddress());
        } else {
            LOG.debug("No consumer to handle message received: byte[]");
        }
    }

};