//,temp,sample_6162.java,2,16,temp,sample_6181.java,2,16
//,2
public class xxx {
public void dummy_method(){
BufferedReader reader;
for (int i = 1; i <= messageCount; ++i) {
reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
OutputStream writer = clientSocket.getOutputStream();
writer.write(message, 0, message.length);
writer.flush();
writer.write('\n');
writer.flush();
Thread.sleep(1000);
}


log.info("message send loop complete closing connection");
}

};