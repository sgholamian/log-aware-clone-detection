//,temp,sample_6805.java,2,10,temp,sample_6239.java,2,11
//,3
public class xxx {
private synchronized void reconnect() throws InterruptedException, IOException, SmackException, XMPPException {
if (!connection.isConnected()) {
if (LOG.isDebugEnabled()) {


log.info("reconnecting to");
}
}
}

};