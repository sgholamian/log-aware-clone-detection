//,temp,sample_6160.java,2,17,temp,sample_6179.java,2,17
//,2
public class xxx {
public void dummy_method(){
clientSocket.setSoTimeout(1000);
clientSocket.connect(serverSocket.getLocalSocketAddress(), 10000);
clientSocket.setTcpNoDelay(true);
byte[] message = "Hello World".getBytes();
BufferedReader reader;
for (int i = 1; i <= messageCount; ++i) {
reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
OutputStream writer = clientSocket.getOutputStream();
writer.write(message, 0, message.length);
writer.flush();


log.info("sending terminator");
}
}

};