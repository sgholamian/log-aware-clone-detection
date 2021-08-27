//,temp,sample_7134.java,2,17,temp,sample_7133.java,2,16
//,3
public class xxx {
public void dummy_method(){
try {
if (config != null && config.getUseGlobalSslContextParameters() != null && config.getUseGlobalSslContextParameters()) {
SSLContextParameters globalSSLParams = context.getSSLContextParameters();
if (globalSSLParams != null) {
ProtocolSocketFactory factory = new SSLContextParametersSecureProtocolSocketFactory(globalSSLParams, context);
Protocol.registerProtocol("https", new Protocol( "https", factory, 443));
}
}
} catch (NoUniqueBeanDefinitionException e) {
} catch (NoSuchBeanDefinitionException e) {


log.info("no instance of sslcontextparameters found");
}
}

};