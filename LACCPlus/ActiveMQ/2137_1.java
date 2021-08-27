//,temp,CommandDatagramSocket.java,200,214,temp,CommandDatagramChannel.java,230,245
//,3
public class xxx {
    protected void sendWriteBuffer(int commandId, SocketAddress address, byte[] data, boolean redelivery) throws IOException {
        // lets put the datagram into the replay buffer first to prevent timing
        // issues
        ReplayBuffer bufferCache = getReplayBuffer();
        if (bufferCache != null && !redelivery) {
            bufferCache.addBuffer(commandId, data);
        }

        if (LOG.isDebugEnabled()) {
            String text = redelivery ? "REDELIVERING" : "sending";
            LOG.debug("Channel: " + name + " " + text + " datagram: " + commandId + " to: " + address);
        }
        DatagramPacket packet = new DatagramPacket(data, 0, data.length, address);
        channel.send(packet);
    }

};