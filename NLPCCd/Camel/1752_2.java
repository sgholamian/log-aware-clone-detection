//,temp,sample_1206.java,2,11,temp,sample_4731.java,2,11
//,2
public class xxx {
private Pubsub buildClient(HttpTransport httpTransport) throws Exception {
GoogleCredential credential = null;
if (!Strings.isNullOrEmpty(serviceAccount) && !Strings.isNullOrEmpty(serviceAccountKey)) {
if (logger.isDebugEnabled()) {


log.info("service account and key have been set explicitly initialising pubsub using service account");
}
}
}

};