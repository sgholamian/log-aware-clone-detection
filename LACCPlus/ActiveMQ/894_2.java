//,temp,MQTTSocket.java,74,94,temp,WSTransportProxy.java,181,200
//,3
public class xxx {
    @Override
    public void onWebSocketText(String data) {
        if (!transportStartedAtLeastOnce()) {
            LOG.debug("Waiting for WebSocket to be properly started...");
            try {
                socketTransportStarted.await();
            } catch (InterruptedException e) {
                LOG.warn("While waiting for WebSocket to be properly started, we got interrupted!! Should be okay, but you could see race conditions...");
            }
        }

        protocolLock.lock();
        try {
            wsTransport.onWebSocketText(data);
        } catch (Exception e) {
            onException(IOExceptionSupport.create(e));
        } finally {
            protocolLock.unlock();
        }
    }

};