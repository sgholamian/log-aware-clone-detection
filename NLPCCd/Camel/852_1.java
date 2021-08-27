//,temp,sample_5450.java,2,17,temp,sample_789.java,2,18
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