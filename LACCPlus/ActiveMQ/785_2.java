//,temp,CommandDatagramSocket.java,65,91,temp,CommandDatagramChannel.java,76,114
//,3
public class xxx {
    public Command read() throws IOException {
        Command answer = null;
        Endpoint from = null;
        synchronized (readLock) {
            while (true) {
                readBuffer.clear();
                SocketAddress address = channel.receive(readBuffer);

                readBuffer.flip();

                if (readBuffer.limit() == 0) {
                    continue;
                }
                
                receiveCounter++;
                from = headerMarshaller.createEndpoint(readBuffer, address);

                int remaining = readBuffer.remaining();
                byte[] data = new byte[remaining];
                readBuffer.get(data);

                // TODO could use a DataInput implementation that talks direct
                // to
                // the ByteBuffer to avoid object allocation and unnecessary
                // buffering?
                DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(data));
                answer = (Command)wireFormat.unmarshal(dataIn);
                break;
            }
        }
        if (answer != null) {
            answer.setFrom(from);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Channel: " + name + " received from: " + from + " about to process: " + answer);
            }
        }
        return answer;
    }

};