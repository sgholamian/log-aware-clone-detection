//,temp,CommandDatagramSocket.java,200,214,temp,CommandDatagramChannel.java,230,245
//,3
public class xxx {
    protected void sendWriteBuffer(int commandId, SocketAddress address, ByteBuffer writeBuffer, boolean redelivery) throws IOException {
        // lets put the datagram into the replay buffer first to prevent timing
        // issues
        ReplayBuffer bufferCache = getReplayBuffer();
        if (bufferCache != null && !redelivery) {
            bufferCache.addBuffer(commandId, writeBuffer);
        }

        writeBuffer.flip();

        if (LOG.isDebugEnabled()) {
            String text = redelivery ? "REDELIVERING" : "sending";
            LOG.debug("Channel: " + name + " " + text + " datagram: " + commandId + " to: " + address);
        }
        channel.send(writeBuffer, address);
    }

};