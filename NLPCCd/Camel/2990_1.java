//,temp,sample_7945.java,2,14,temp,sample_7943.java,2,14
//,3
public class xxx {
public String eatData(int timeout) throws SocketException {
clientSocket.setSoTimeout(timeout);
StringBuilder availableInput = new StringBuilder();
try {
while (0 < inputStream.available()) {
availableInput.append((char) inputStream.read());
}
} catch (IOException e) {


log.info("exception encountered eating available input");
}
}

};