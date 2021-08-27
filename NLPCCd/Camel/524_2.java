//,temp,sample_1571.java,2,12,temp,sample_2516.java,2,10
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