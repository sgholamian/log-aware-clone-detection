//,temp,MQTTWSConnection.java,272,280,temp,StompWSConnection.java,134,141
//,2
public class xxx {
    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        LOG.trace("STOMP WS Connection closed, code:{} message:{}", statusCode, reason);

        this.connection = null;
        this.closeCode = statusCode;
        this.closeMessage = reason;
    }

};