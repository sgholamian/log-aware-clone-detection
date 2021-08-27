//,temp,UnreliableCommandDatagramChannel.java,48,58,temp,UnreliableCommandDatagramSocket.java,44,56
//,3
public class xxx {
    protected void sendWriteBuffer(int commandId, SocketAddress address, byte[] data, boolean redelivery) throws IOException {
        if (dropCommandStrategy.shouldDropCommand(commandId, address, redelivery)) {
            LOG.info("Dropping datagram with command: " + commandId);

            // lets still add it to the replay buffer though!
            ReplayBuffer bufferCache = getReplayBuffer();
            if (bufferCache != null && !redelivery) {
                bufferCache.addBuffer(commandId, data);
            }
        } else {
            super.sendWriteBuffer(commandId, address, data, redelivery);
        }
    }

};