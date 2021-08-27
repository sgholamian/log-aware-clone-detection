//,temp,sample_7865.java,2,14,temp,sample_3822.java,2,14
//,3
public class xxx {
private String sendAndReceiveUdpMessages(String input) throws Exception {
DatagramSocket socket = new DatagramSocket();
InetAddress address = InetAddress.getByName("127.0.0.1");
byte[] data = (input + "\n").getBytes();
DatagramPacket packet = new DatagramPacket(data, data.length, address, getPort());
socket.send(packet);
Thread.sleep(1000);
byte[] buf = new byte[128];
DatagramPacket receive = new DatagramPacket(buf, buf.length, address, getPort());


log.info("receiving data");
}

};