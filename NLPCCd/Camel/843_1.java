//,temp,sample_1954.java,2,18,temp,sample_3123.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (!configuration.isSsl()) {
return null;
}
SSLContext answer;
if (configuration.getSslContextParameters() != null) {
answer = configuration.getSslContextParameters().createSSLContext(camelContext);
} else {
if (configuration.getKeyStoreFile() == null && configuration.getKeyStoreResource() == null) {
}
if (configuration.getTrustStoreFile() == null && configuration.getTrustStoreResource() == null) {


log.info("truststorefile is null");
}
}
}

};