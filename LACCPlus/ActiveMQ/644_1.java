//,temp,CommandDatagramSocket.java,216,225,temp,CommandDatagramChannel.java,247,256
//,3
public class xxx {
    public void sendBuffer(int commandId, Object buffer) throws IOException {
        if (buffer != null) {
            byte[] data = (byte[])buffer;
            sendWriteBuffer(commandId, replayAddress, data, true);
        } else {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Request for buffer: " + commandId + " is no longer present");
            }
        }
    }

};