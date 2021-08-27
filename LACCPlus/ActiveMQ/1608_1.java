//,temp,UnreliableCommandDatagramChannel.java,48,58,temp,UnreliableCommandDatagramSocket.java,44,56
//,3
public class xxx {
    protected void sendWriteBuffer(int commandId, SocketAddress address, ByteBuffer writeBuffer, boolean redelivery) throws IOException {
        if (dropCommandStrategy.shouldDropCommand(commandId, address, redelivery)) {
            writeBuffer.flip();
            LOG.info("Dropping datagram with command: " + commandId);

            // lets still add it to the replay buffer though!
            getReplayBuffer().addBuffer(commandId, writeBuffer);
        } else {
            super.sendWriteBuffer(commandId, address, writeBuffer, redelivery);
        }
    }

};