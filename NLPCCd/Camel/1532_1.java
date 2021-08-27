//,temp,sample_2377.java,2,15,temp,sample_2378.java,2,17
//,3
public class xxx {
protected int poll() throws Exception {
shutdownRunningTask = null;
pendingExchanges = 0;
int polledMessages = 0;
ensureIsConnected();
if (store == null || folder == null) {
throw new IllegalStateException("MailConsumer did not connect properly to the MailStore: " + getEndpoint().getConfiguration().getMailStoreLogInformation());
}
if (LOG.isDebugEnabled()) {


log.info("polling mailbox folder");
}
}

};