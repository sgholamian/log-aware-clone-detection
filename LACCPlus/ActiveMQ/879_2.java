//,temp,WSTransportProxy.java,202,216,temp,StompSocket.java,69,83
//,3
public class xxx {
    @Override
    public void onWebSocketClose(int arg0, String arg1) {
        try {
            if (protocolLock.tryLock() || protocolLock.tryLock(ORDERLY_CLOSE_TIMEOUT, TimeUnit.SECONDS)) {
                LOG.debug("Stomp WebSocket closed: code[{}] message[{}]", arg0, arg1);
                protocolConverter.onStompCommand(new StompFrame(Stomp.Commands.DISCONNECT));
            }
        } catch (Exception e) {
            LOG.debug("Failed to close STOMP WebSocket cleanly", e);
        } finally {
            if (protocolLock.isHeldByCurrentThread()) {
                protocolLock.unlock();
            }
        }
    }

};