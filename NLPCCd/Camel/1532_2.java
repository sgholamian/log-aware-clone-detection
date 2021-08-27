//,temp,sample_2377.java,2,15,temp,sample_2378.java,2,17
//,3
public class xxx {
public void dummy_method(){
shutdownRunningTask = null;
pendingExchanges = 0;
int polledMessages = 0;
ensureIsConnected();
if (store == null || folder == null) {
throw new IllegalStateException("MailConsumer did not connect properly to the MailStore: " + getEndpoint().getConfiguration().getMailStoreLogInformation());
}
if (LOG.isDebugEnabled()) {
}
if (getEndpoint().getConfiguration().getFetchSize() == 0) {


log.info("fetch size is meaning the configuration is set to poll no new messages at all camel will skip this poll");
}
}

};