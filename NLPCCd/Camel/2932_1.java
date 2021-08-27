//,temp,sample_5175.java,2,14,temp,sample_4300.java,2,14
//,3
public class xxx {
public DefaultServerPipelineFactory(NettyServerBootstrapConfiguration configuration) {
this.consumer = null;
try {
this.sslContext = createSSLContext(null, configuration);
} catch (Exception e) {
throw ObjectHelper.wrapRuntimeCamelException(e);
}
if (sslContext != null) {


log.info("created sslcontext");
}
}

};