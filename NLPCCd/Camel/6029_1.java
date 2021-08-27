//,temp,sample_2095.java,2,15,temp,sample_1950.java,2,14
//,3
public class xxx {
public HttpServerPipelineFactory(NettyHttpConsumer nettyConsumer) {
this.consumer = nettyConsumer;
this.configuration = nettyConsumer.getConfiguration();
try {
this.sslContext = createSSLContext(consumer.getContext(), consumer.getConfiguration());
} catch (Exception e) {
throw ObjectHelper.wrapRuntimeCamelException(e);
}
if (sslContext != null) {


log.info("created sslcontext");
}
}

};