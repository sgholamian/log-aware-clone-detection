//,temp,WSTransportProxy.java,202,216,temp,StompSocket.java,69,83
//,3
public class xxx {
    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        try {
            if (protocolLock.tryLock() || protocolLock.tryLock(ORDERLY_CLOSE_TIMEOUT, TimeUnit.SECONDS)) {
                LOG.debug("WebSocket closed: code[{}] message[{}]", statusCode, reason);
                wsTransport.onWebSocketClosed();
            }
        } catch (Exception e) {
            LOG.debug("Failed to close WebSocket cleanly", e);
        } finally {
            if (protocolLock.isHeldByCurrentThread()) {
                protocolLock.unlock();
            }
        }
    }

};