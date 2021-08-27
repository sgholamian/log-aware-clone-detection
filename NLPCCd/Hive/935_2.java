//,temp,sample_4413.java,2,19,temp,sample_647.java,2,19
//,3
public class xxx {
public void dummy_method(){
TServerSocket thriftServerSocket = TSSLTransportFactory.getServerSocket(portNum, 0, serverAddress.getAddress(), params);
if (thriftServerSocket.getServerSocket() instanceof SSLServerSocket) {
List<String> sslVersionBlacklistLocal = new ArrayList<>();
for (String sslVersion : sslVersionBlacklist) {
sslVersionBlacklistLocal.add(sslVersion.trim().toLowerCase());
}
SSLServerSocket sslServerSocket = (SSLServerSocket) thriftServerSocket.getServerSocket();
List<String> enabledProtocols = new ArrayList<>();
for (String protocol : sslServerSocket.getEnabledProtocols()) {
if (sslVersionBlacklistLocal.contains(protocol.toLowerCase())) {


log.info("disabling ssl protocol");
}
}
}
}

};