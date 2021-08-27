//,temp,MQTTSocket.java,74,94,temp,WSTransportProxy.java,181,200
//,3
public class xxx {
    @Override
    public void onWebSocketBinary(byte[] bytes, int offset, int length) {
        if (!transportStartedAtLeastOnce()) {
            LOG.debug("Waiting for MQTTSocket to be properly started...");
            try {
                socketTransportStarted.await();
            } catch (InterruptedException e) {
                LOG.warn("While waiting for MQTTSocket to be properly started, we got interrupted!! Should be okay, but you could see race conditions...");
            }
        }

        protocolLock.lock();
        try {
            receiveCounter += length;
            codec.parse(new DataByteArrayInputStream(new Buffer(bytes, offset, length)), length);
        } catch (Exception e) {
            onException(IOExceptionSupport.create(e));
        } finally {
            protocolLock.unlock();
        }
    }

};