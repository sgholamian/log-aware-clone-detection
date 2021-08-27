//,temp,sample_4732.java,2,16,temp,sample_1207.java,2,16
//,2
public class xxx {
private Bigquery buildClient(HttpTransport httpTransport) throws Exception {
GoogleCredential credential = null;
if (!Strings.isNullOrEmpty(serviceAccount) && !Strings.isNullOrEmpty(serviceAccountKey)) {
if (logger.isDebugEnabled()) {
}
credential = createFromAccountKeyPair(httpTransport);
}
if (credential == null && !Strings.isNullOrEmpty(credentialsFileLocation)) {
if (logger.isDebugEnabled()) {


log.info("key file name has been set explicitly initialising bigquery using key file");
}
}
}

};