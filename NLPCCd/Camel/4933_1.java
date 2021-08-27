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


log.info("unbinding from server address using acceptor");
}
}

};