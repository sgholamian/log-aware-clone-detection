//,temp,sample_5044.java,2,14,temp,sample_5045.java,2,18
//,3
public class xxx {
protected void doStop() throws Exception {
if (configuration.isClientMode() && configuration.getProtocol().equals("tcp")) {
if (session != null) {
CloseFuture closeFuture = session.closeNow();
closeFuture.awaitUninterruptibly();
}
connector.dispose(true);
} else {
if (address instanceof InetSocketAddress) {
if ("0.0.0.0".equals(((InetSocketAddress)address).getAddress().getHostAddress())) {


log.info("unbind the server address");
}
}
}
}

};