//,temp,sample_2099.java,2,18,temp,sample_3122.java,2,17
//,3
public class xxx {
private SSLContext createSSLContext(NettyProducer producer) throws Exception {
NettyConfiguration configuration = producer.getConfiguration();
if (!configuration.isSsl()) {
return null;
}
SSLContext answer;
if (configuration.getSslContextParameters() != null) {
answer = configuration.getSslContextParameters().createSSLContext(producer.getContext());
} else {
if (configuration.getKeyStoreFile() == null && configuration.getKeyStoreResource() == null) {


log.info("keystorefile is null");
}
}
}

};