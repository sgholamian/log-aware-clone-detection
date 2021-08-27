//,temp,HttpClientInitializerFactory.java,81,132,temp,HttpServerInitializerFactory.java,78,135
//,3
public class xxx {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        // create a new pipeline
        ChannelPipeline pipeline = ch.pipeline();

        ChannelHandler sslHandler = configureServerSSLOnDemand();
        if (sslHandler != null) {
            //TODO must close on SSL exception
            // sslHandler.setCloseOnSSLException(true);

            if (sslHandler instanceof ChannelHandlerFactory) {
                // use the factory to create a new instance of the channel as it may not be shareable
                sslHandler = ((ChannelHandlerFactory) sslHandler).newChannelHandler();
            }

            LOG.debug("Server SSL handler configured and added as an interceptor against the ChannelPipeline: {}", sslHandler);
            pipeline.addLast("ssl", sslHandler);
        }

        pipeline.addLast("decoder", new HttpRequestDecoder(4096, configuration.getMaxHeaderSize(), 8192));
        List<ChannelHandler> decoders = consumer.getConfiguration().getDecoders();
        for (int x = 0; x < decoders.size(); x++) {
            ChannelHandler decoder = decoders.get(x);
            if (decoder instanceof ChannelHandlerFactory) {
                // use the factory to create a new instance of the channel as it may not be shareable
                decoder = ((ChannelHandlerFactory) decoder).newChannelHandler();
            }
            pipeline.addLast("decoder-" + x, decoder);
        }
        pipeline.addLast("encoder", new HttpResponseEncoder());
        List<ChannelHandler> encoders = consumer.getConfiguration().getEncoders();
        for (int x = 0; x < encoders.size(); x++) {
            ChannelHandler encoder = encoders.get(x);
            if (encoder instanceof ChannelHandlerFactory) {
                // use the factory to create a new instance of the channel as it may not be shareable
                encoder = ((ChannelHandlerFactory) encoder).newChannelHandler();
            }
            pipeline.addLast("encoder-" + x, encoder);
        }
        if (configuration.isDisableStreamCache()) {
            pipeline.addLast("inbound-streamer", new HttpInboundStreamHandler());
        }
        pipeline.addLast("aggregator", new HttpObjectAggregator(configuration.getChunkedMaxContentLength()));
        pipeline.addLast("outbound-streamer", new HttpOutboundStreamHandler());
        if (supportCompressed()) {
            pipeline.addLast("deflater", new HttpContentCompressor());
        }

        int port = consumer.getConfiguration().getPort();
        ChannelHandler handler = consumer.getEndpoint().getComponent().getMultiplexChannelHandler(port).getChannelHandler();

        if (consumer.getConfiguration().isUsingExecutorService()) {
            EventExecutorGroup applicationExecutor = consumer.getEndpoint().getComponent().getExecutorService();
            pipeline.addLast(applicationExecutor, "handler", handler);
        } else {
            pipeline.addLast("handler", handler);
        }
    }

};