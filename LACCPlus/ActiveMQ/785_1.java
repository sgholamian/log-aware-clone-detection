//,temp,CommandDatagramSocket.java,65,91,temp,CommandDatagramChannel.java,76,114
//,3
public class xxx {
    public Command read() throws IOException {
        Command answer = null;
        Endpoint from = null;
        synchronized (readLock) {
            while (true) {
                DatagramPacket datagram = createDatagramPacket();
                channel.receive(datagram);

                // TODO could use a DataInput implementation that talks direct
                // to the byte[] to avoid object allocation
                receiveCounter++;
                DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(datagram.getData(), 0, datagram.getLength()));
                
                from = headerMarshaller.createEndpoint(datagram, dataIn);
                answer = (Command)wireFormat.unmarshal(dataIn);
                break;
            }
        }
        if (answer != null) {
            answer.setFrom(from);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Channel: " + name + " about to process: " + answer);
            }
        }
        return answer;
    }

};