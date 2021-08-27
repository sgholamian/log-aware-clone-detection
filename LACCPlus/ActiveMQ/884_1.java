//,temp,WSTransportProxy.java,252,276,temp,WSTransportProxy.java,233,250
//,3
public class xxx {
    @Override
    public void onSocketOutboundBinary(ByteBuffer data) throws IOException {
        if (!transportStartedAtLeastOnce()) {
            LOG.debug("Waiting for WebSocket to be properly started...");
            try {
                socketTransportStarted.await();
            } catch (InterruptedException e) {
                LOG.warn("While waiting for WebSocket to be properly started, we got interrupted!! Should be okay, but you could see race conditions...");
            }
        }

        LOG.trace("WS Proxy sending {} bytes out", data.remaining());
        int limit = data.limit();
        try {
            session.getRemote().sendBytesByFuture(data).get(getDefaultSendTimeOut(), TimeUnit.SECONDS);
        } catch (Exception e) {
            throw IOExceptionSupport.create(e);
        }

        // Reset back to original limit and move position to match limit indicating
        // that we read everything, the websocket sender clears the passed buffer
        // which can make it look as if nothing was written.
        data.limit(limit);
        data.position(limit);
    }

};