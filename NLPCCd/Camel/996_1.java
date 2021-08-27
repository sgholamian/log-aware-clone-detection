//,temp,sample_8033.java,2,10,temp,sample_3551.java,2,10
//,3
public class xxx {
private String sendAndReceiveUdpMessages(String input) throws Exception {
DatagramSocket socket = new DatagramSocket();
InetAddress address = InetAddress.getByName("127.0.0.1");
byte[] data = (input + "\n").getBytes();
DatagramPacket packet = new DatagramPacket(data, data.length, address, getPort());


log.info("sending data");
}

};