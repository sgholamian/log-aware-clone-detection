//,temp,sample_1953.java,2,16,temp,sample_2183.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (!configuration.isSsl()) {
return null;
}
SSLContext answer;
if (configuration.getSslContextParameters() != null) {
answer = configuration.getSslContextParameters().createSSLContext();
} else {
if (configuration.getKeyStoreFile() == null && configuration.getKeyStoreResource() == null) {
}
if (configuration.getTrustStoreFile() == null && configuration.getTrustStoreResource() == null) {


log.info("truststorefile is null");
}
}
}

};