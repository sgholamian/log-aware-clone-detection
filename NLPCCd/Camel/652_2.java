//,temp,sample_2182.java,2,16,temp,sample_2758.java,2,16
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