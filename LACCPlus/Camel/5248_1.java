//,temp,DefaultClientInitializerFactory.java,52,99,temp,DefaultServerInitializerFactory.java,70,113
//,3
public class xxx {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        // create a new pipeline
        ChannelPipeline channelPipeline = ch.pipeline();

        SslHandler sslHandler = configureClientSSLOnDemand();
        if (sslHandler != null) {
            //TODO  must close on SSL exception
            //sslHandler.setCloseOnSSLException(true);
            LOG.debug("Client SSL handler configured and added to the ChannelPipeline: {}", sslHandler);
            addToPipeline("ssl", channelPipeline, sslHandler);
        }

        List<ChannelHandler> decoders = producer.getConfiguration().getDecoders();
        for (int x = 0; x < decoders.size(); x++) {
            ChannelHandler decoder = decoders.get(x);
            if (decoder instanceof ChannelHandlerFactory) {
                // use the factory to create a new instance of the channel as it may not be shareable
                decoder = ((ChannelHandlerFactory) decoder).newChannelHandler();
            }
            addToPipeline("decoder-" + x, channelPipeline, decoder);
        }

        List<ChannelHandler> encoders = producer.getConfiguration().getEncoders();
        for (int x = 0; x < encoders.size(); x++) {
            ChannelHandler encoder = encoders.get(x);
            if (encoder instanceof ChannelHandlerFactory) {
                // use the factory to create a new instance of the channel as it may not be shareable
                encoder = ((ChannelHandlerFactory) encoder).newChannelHandler();
            }
            addToPipeline("encoder-" + x, channelPipeline, encoder);
        }

        // do we use request timeout?
        if (producer.getConfiguration().getRequestTimeout() > 0) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Using request timeout {} millis", producer.getConfiguration().getRequestTimeout());
            }
            ChannelHandler timeout
                    = new ReadTimeoutHandler(producer.getConfiguration().getRequestTimeout(), TimeUnit.MILLISECONDS);
            addToPipeline("timeout", channelPipeline, timeout);
        }

        // our handler must be added last
        addToPipeline("handler", channelPipeline, new ClientChannelHandler(producer));

        LOG.trace("Created ChannelPipeline: {}", channelPipeline);
    }

};