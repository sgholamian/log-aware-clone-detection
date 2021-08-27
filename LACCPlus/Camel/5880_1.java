//,temp,HttpServerInitializerFactory.java,59,71,temp,DefaultServerInitializerFactory.java,43,55
//,3
public class xxx {
    public HttpServerInitializerFactory(NettyHttpConsumer nettyConsumer) {
        this.consumer = nettyConsumer;
        this.configuration = nettyConsumer.getConfiguration();
        try {
            this.sslContext = createSSLContext(consumer.getContext(), consumer.getConfiguration());
        } catch (Exception e) {
            throw RuntimeCamelException.wrapRuntimeCamelException(e);
        }

        if (sslContext != null) {
            LOG.info("Created SslContext {}", sslContext);
        }
    }

};