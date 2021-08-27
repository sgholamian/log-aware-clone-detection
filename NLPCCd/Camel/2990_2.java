//,temp,sample_7945.java,2,14,temp,sample_7943.java,2,14
//,3
public class xxx {
public String receiveData(int timeout) throws SocketException, SocketTimeoutException {
clientSocket.setSoTimeout(timeout);
StringBuilder availableInput = new StringBuilder();
try {
do {
availableInput.append((char) inputStream.read());
} while (0 < inputStream.available());
} catch (SocketTimeoutException timeoutEx) {


log.info("timeout while receiving available input");
}
}

};