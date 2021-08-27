//,temp,HttpClientInitializerFactory.java,81,132,temp,HttpServerInitializerFactory.java,78,135
//,3
public class xxx {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        // create a new pipeline
        ChannelPipeline pipeline = ch.pipeline();

        SslHandler sslHandler = configureClientSSLOnDemand();
        if (sslHandler != null) {
            //TODO must close on SSL exception
            //sslHandler.setCloseOnSSLException(true);
            LOG.debug("Client SSL handler configured and added as an interceptor against the ChannelPipeline: {}", sslHandler);
            pipeline.addLast("ssl", sslHandler);
        }

        pipeline.addLast("http", new HttpClientCodec());

        List<ChannelHandler> encoders = producer.getConfiguration().getEncoders();
        for (int x = 0; x < encoders.size(); x++) {
            ChannelHandler encoder = encoders.get(x);
            if (encoder instanceof ChannelHandlerFactory) {
                // use the factory to create a new instance of the channel as it may not be shareable
                encoder = ((ChannelHandlerFactory) encoder).newChannelHandler();
            }
            pipeline.addLast("encoder-" + x, encoder);
        }

        List<ChannelHandler> decoders = producer.getConfiguration().getDecoders();
        for (int x = 0; x < decoders.size(); x++) {
            ChannelHandler decoder = decoders.get(x);
            if (decoder instanceof ChannelHandlerFactory) {
                // use the factory to create a new instance of the channel as it may not be shareable
                decoder = ((ChannelHandlerFactory) decoder).newChannelHandler();
            }
            pipeline.addLast("decoder-" + x, decoder);
        }
        if (configuration.isDisableStreamCache()) {
            pipeline.addLast("inbound-streamer", new HttpInboundStreamHandler());
        }
        pipeline.addLast("aggregator", new HttpObjectAggregator(configuration.getChunkedMaxContentLength()));
        pipeline.addLast("outbound-streamer", new HttpOutboundStreamHandler());

        if (producer.getConfiguration().getRequestTimeout() > 0) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Using request timeout {} millis", producer.getConfiguration().getRequestTimeout());
            }
            ChannelHandler timeout
                    = new ReadTimeoutHandler(producer.getConfiguration().getRequestTimeout(), TimeUnit.MILLISECONDS);
            pipeline.addLast("timeout", timeout);
        }

        // handler to route Camel messages
        pipeline.addLast("handler", new HttpClientChannelHandler(producer));
    }

};