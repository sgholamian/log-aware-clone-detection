//,temp,NettyUdpWithInOutUsingPlainSocketTest.java,43,64,temp,MinaUdpWithInOutUsingPlainSocketTest.java,45,65
//,3
public class xxx {
    private String sendAndReceiveUdpMessages(String input) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("127.0.0.1");

        // must append delimiter
        byte[] data = (input + "\n").getBytes();

        DatagramPacket packet = new DatagramPacket(data, data.length, address, getPort());
        LOG.debug("+++ Sending data +++");
        socket.send(packet);

        Thread.sleep(1000);

        byte[] buf = new byte[128];
        DatagramPacket receive = new DatagramPacket(buf, buf.length, address, getPort());
        LOG.debug("+++ Receiving data +++");
        socket.receive(receive);

        socket.close();

        return new String(receive.getData(), 0, receive.getLength());
    }

};