//,temp,DefaultClientInitializerFactory.java,52,99,temp,DefaultServerInitializerFactory.java,70,113
//,3
public class xxx {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        // create a new pipeline
        ChannelPipeline channelPipeline = ch.pipeline();

        SslHandler sslHandler = configureServerSSLOnDemand();
        if (sslHandler != null) {
            //TODO  must close on SSL exception
            //sslHandler.setCloseOnSSLException(true);
            LOG.debug("Server SSL handler configured and added as an interceptor against the ChannelPipeline: {}", sslHandler);
            addToPipeline("ssl", channelPipeline, sslHandler);
        }

        List<ChannelHandler> encoders = consumer.getConfiguration().getEncoders();
        for (int x = 0; x < encoders.size(); x++) {
            ChannelHandler encoder = encoders.get(x);
            if (encoder instanceof ChannelHandlerFactory) {
                // use the factory to create a new instance of the channel as it may not be shareable
                encoder = ((ChannelHandlerFactory) encoder).newChannelHandler();
            }
            addToPipeline("encoder-" + x, channelPipeline, encoder);
        }

        List<ChannelHandler> decoders = consumer.getConfiguration().getDecoders();
        for (int x = 0; x < decoders.size(); x++) {
            ChannelHandler decoder = decoders.get(x);
            if (decoder instanceof ChannelHandlerFactory) {
                // use the factory to create a new instance of the channel as it may not be shareable
                decoder = ((ChannelHandlerFactory) decoder).newChannelHandler();
            }
            addToPipeline("decoder-" + x, channelPipeline, decoder);
        }

        if (consumer.getConfiguration().isUsingExecutorService()) {
            // Just use EventExecutorGroup from the Netty Component
            EventExecutorGroup applicationExecutor = consumer.getEndpoint().getComponent().getExecutorService();
            addToPipeline("handler", channelPipeline, applicationExecutor, new ServerChannelHandler(consumer));
        } else {
            // still use the worker event loop group here
            addToPipeline("handler", channelPipeline, new ServerChannelHandler(consumer));

        }
        LOG.trace("Created ChannelPipeline: {}", channelPipeline);
    }

};