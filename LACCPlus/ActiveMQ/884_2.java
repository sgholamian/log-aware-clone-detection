//,temp,WSTransportProxy.java,252,276,temp,WSTransportProxy.java,233,250
//,3
public class xxx {
    @Override
    public void onSocketOutboundText(String data) throws IOException {
        if (!transportStartedAtLeastOnce()) {
            LOG.debug("Waiting for WebSocket to be properly started...");
            try {
                socketTransportStarted.await();
            } catch (InterruptedException e) {
                LOG.warn("While waiting for WebSocket to be properly started, we got interrupted!! Should be okay, but you could see race conditions...");
            }
        }

        LOG.trace("WS Proxy sending string of size {} out", data.length());
        try {
            session.getRemote().sendStringByFuture(data).get(getDefaultSendTimeOut(), TimeUnit.SECONDS);
        } catch (Exception e) {
            throw IOExceptionSupport.create(e);
        }
    }

};