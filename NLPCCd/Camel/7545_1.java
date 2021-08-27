//,temp,sample_6852.java,2,18,temp,sample_6851.java,2,17
//,3
public class xxx {
public void dummy_method(){
this.setName("MllpServerResource$AcceptSocketThread - " + serverSocket.getLocalSocketAddress().toString());
while (!isInterrupted() && serverSocket.isBound() && !serverSocket.isClosed()) {
Socket clientSocket = null;
try {
clientSocket = serverSocket.accept();
} catch (SocketTimeoutException timeoutEx) {
if (raiseExceptionOnAcceptTimeout) {
throw new MllpJUnitResourceTimeoutException("Timeout Accepting client connection", timeoutEx);
}
} catch (SocketException socketEx) {


log.info("socketexception encountered accepting client connection ignoring");
}
}
}

};