//,temp,sample_5175.java,2,14,temp,sample_4300.java,2,14
//,3
public class xxx {
public HttpClientInitializerFactory(NettyHttpProducer nettyProducer) {
this.producer = nettyProducer;
try {
this.sslContext = createSSLContext(producer);
} catch (Exception e) {
throw ObjectHelper.wrapRuntimeCamelException(e);
}
if (sslContext != null) {


log.info("created sslcontext");
}
}

};