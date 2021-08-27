//,temp,sample_6883.java,2,12,temp,sample_6859.java,2,15
//,3
public class xxx {
public void interrupt() {
for (ClientSocketThread clientSocketThread : clientSocketThreads) {
clientSocketThread.interrupt();
}
if (serverSocket != null && serverSocket.isBound() && !serverSocket.isClosed()) {
try {
serverSocket.close();
} catch (Exception ex) {


log.info("exception encountered closing server socket on interrupt");
}
}
}

};