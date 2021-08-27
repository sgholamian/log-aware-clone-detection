//,temp,sample_6908.java,2,16,temp,sample_7924.java,2,16
//,3
public class xxx {
public void reset() {
try {
clientSocket.setSoLinger(true, 0);
} catch (SocketException socketEx) {
}
try {
if (null != inputStream) {
clientSocket.close();
}
} catch (IOException e) {


log.info("exception encountered resetting connection to");
}
}

};