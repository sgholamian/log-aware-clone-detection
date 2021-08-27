//,temp,HttpClientInitializerFactory.java,62,74,temp,DefaultServerInitializerFactory.java,57,68
//,3
public class xxx {
    public HttpClientInitializerFactory(NettyHttpProducer nettyProducer) {
        this.producer = nettyProducer;
        try {
            this.sslContext = createSSLContext(producer);
        } catch (Exception e) {
            throw RuntimeCamelException.wrapRuntimeCamelException(e);
        }

        if (sslContext != null) {
            LOG.info("Created SslContext {}", sslContext);
        }
        configuration = nettyProducer.getConfiguration();
    }

};