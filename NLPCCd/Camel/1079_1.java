//,temp,sample_5180.java,2,16,temp,sample_5451.java,2,18
//,3
public class xxx {
private SSLContext createSSLContext(CamelContext camelContext, NettyServerBootstrapConfiguration configuration) throws Exception {
if (!configuration.isSsl()) {
return null;
}
SSLContext answer;
if (configuration.getSslContextParameters() != null) {
answer = configuration.getSslContextParameters().createSSLContext(camelContext);
} else {
if (configuration.getKeyStoreFile() == null && configuration.getKeyStoreResource() == null) {


log.info("keystorefile is null");
}
}
}

};