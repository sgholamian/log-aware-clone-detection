//,temp,sample_7926.java,2,14,temp,sample_7925.java,2,11
//,3
public class xxx {
public void sendData(String data, boolean disconnectAfterSend) {
byte[] payloadBytes = data.getBytes();
try {
outputStream.write(payloadBytes, 0, payloadBytes.length);
} catch (IOException e) {


log.info("unable to send raw string");
}
}

};