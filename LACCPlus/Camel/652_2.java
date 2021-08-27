//,temp,HttpClientInitializerFactory.java,62,74,temp,DefaultServerInitializerFactory.java,57,68
//,3
public class xxx {
    public DefaultServerInitializerFactory(NettyConsumer consumer) {
        this.consumer = consumer;
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