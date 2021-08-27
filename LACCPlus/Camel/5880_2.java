//,temp,HttpServerInitializerFactory.java,59,71,temp,DefaultServerInitializerFactory.java,43,55
//,3
public class xxx {
    @Deprecated
    public DefaultServerInitializerFactory(NettyServerBootstrapConfiguration configuration) {
        this.consumer = null;
        try {
            this.sslContext = createSSLContext(null, configuration);
        } catch (Exception e) {
            throw RuntimeCamelException.wrapRuntimeCamelException(e);
        }

        if (sslContext != null) {
            LOG.info("Created SslContext {}", sslContext);
        }
    }

};