//,temp,sample_3291.java,2,16,temp,sample_4303.java,2,17
//,3
public class xxx {
private SSLContext createSSLContext() throws Exception {
if (!configuration.isSsl()) {
return null;
}
SSLContext answer;
if (configuration.getSslContextParameters() != null) {
answer = configuration.getSslContextParameters().createSSLContext();
} else {
if (configuration.getKeyStoreFile() == null && configuration.getKeyStoreResource() == null) {


log.info("keystorefile is null");
}
}
}

};