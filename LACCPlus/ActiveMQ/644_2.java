//,temp,CommandDatagramSocket.java,216,225,temp,CommandDatagramChannel.java,247,256
//,3
public class xxx {
    public void sendBuffer(int commandId, Object buffer) throws IOException {
        if (buffer != null) {
            ByteBuffer writeBuffer = (ByteBuffer)buffer;
            sendWriteBuffer(commandId, getReplayAddress(), writeBuffer, true);
        } else {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Request for buffer: " + commandId + " is no longer present");
            }
        }
    }

};