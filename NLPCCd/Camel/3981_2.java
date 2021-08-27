//,temp,sample_2756.java,2,15,temp,sample_5176.java,2,14
//,3
public class xxx {
public DefaultServerPipelineFactory(NettyConsumer consumer) {
this.consumer = consumer;
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