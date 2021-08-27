//,temp,sample_6159.java,2,17,temp,sample_6161.java,2,17
//,3
public class xxx {
public void dummy_method(){
clientSocket.setTcpNoDelay(true);
byte[] message = "Hello World".getBytes();
BufferedReader reader;
for (int i = 1; i <= messageCount; ++i) {
reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
OutputStream writer = clientSocket.getOutputStream();
writer.write(message, 0, message.length);
writer.flush();
writer.write('\n');
writer.flush();


log.info("received response");
}
}

};