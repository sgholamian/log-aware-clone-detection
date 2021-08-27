//,temp,sample_3289.java,2,16,temp,sample_2180.java,2,16
//,2
public class xxx {
public HttpServerSharedInitializerFactory(NettySharedHttpServerBootstrapConfiguration configuration, HttpServerConsumerChannelFactory channelFactory, ClassResolver classResolver) {
this.configuration = configuration;
this.channelFactory = channelFactory;
this.classResolver = classResolver != null ? classResolver : new DefaultClassResolver();
try {
this.sslContext = createSSLContext();
} catch (Exception e) {
throw ObjectHelper.wrapRuntimeCamelException(e);
}
if (sslContext != null) {


log.info("created sslcontext");
}
}

};