//,temp,sample_6883.java,2,12,temp,sample_6859.java,2,15
//,3
public class xxx {
public void interrupt() {
if (clientSocket != null && clientSocket.isConnected() && !clientSocket.isClosed()) {
try {
clientSocket.close();
} catch (Exception ex) {


log.info("exception encountered closing client socket on interrput");
}
}
}

};