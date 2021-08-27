//,temp,sample_1949.java,2,14,temp,sample_785.java,2,14
//,3
public class xxx {
public HttpClientPipelineFactory(NettyHttpProducer nettyProducer) {
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