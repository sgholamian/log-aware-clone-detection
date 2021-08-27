//,temp,SingleTCPNettyServerBootstrapFactory.java,121,188,temp,SingleUDPNettyServerBootstrapFactory.java,119,197
//,3
public class xxx {
    protected void startServerBootstrap() throws Exception {
        // prefer using explicit configured thread pools
        EventLoopGroup bg = configuration.getBossGroup();
        EventLoopGroup wg = configuration.getWorkerGroup();

        if (bg == null) {
            // create new pool which we should shutdown when stopping as its not shared
            bossGroup = new NettyServerBossPoolBuilder()
                    .withNativeTransport(configuration.isNativeTransport())
                    .withBossCount(configuration.getBossCount())
                    .withName("NettyServerTCPBoss")
                    .build();
            bg = bossGroup;
        }
        if (wg == null) {
            // create new pool which we should shutdown when stopping as its not shared
            workerGroup = new NettyWorkerPoolBuilder()
                    .withNativeTransport(configuration.isNativeTransport())
                    .withWorkerCount(configuration.getWorkerCount())
                    .withName("NettyServerTCPWorker")
                    .build();
            wg = workerGroup;
        }

        serverBootstrap = new ServerBootstrap();
        if (configuration.isNativeTransport()) {
            serverBootstrap.group(bg, wg).channel(EpollServerSocketChannel.class);
        } else {
            serverBootstrap.group(bg, wg).channel(NioServerSocketChannel.class);
        }
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, configuration.isKeepAlive());
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, configuration.isTcpNoDelay());
        serverBootstrap.option(ChannelOption.SO_REUSEADDR, configuration.isReuseAddress());
        serverBootstrap.childOption(ChannelOption.SO_REUSEADDR, configuration.isReuseAddress());
        serverBootstrap.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, configuration.getConnectTimeout());
        if (configuration.getBacklog() > 0) {
            serverBootstrap.option(ChannelOption.SO_BACKLOG, configuration.getBacklog());
        }

        Map<String, Object> options = configuration.getOptions();
        if (options != null) {
            for (Map.Entry<String, Object> entry : options.entrySet()) {
                String value = entry.getValue().toString();
                ChannelOption<Object> option = ChannelOption.valueOf(entry.getKey());
                //For all netty options that aren't of type String
                //TODO: find a way to add primitive Netty options without having to add them to the Camel registry.
                if (EndpointHelper.isReferenceParameter(value)) {
                    String name = value.substring(1);
                    Object o = CamelContextHelper.mandatoryLookup(camelContext, name);
                    serverBootstrap.option(option, o);
                } else {
                    serverBootstrap.option(option, value);
                }
            }
        }

        // set the pipeline factory, which creates the pipeline for each newly created channels
        serverBootstrap.childHandler(pipelineFactory);

        LOG.debug("Created ServerBootstrap {}", serverBootstrap);

        LOG.info("ServerBootstrap binding to {}:{}", configuration.getHost(), configuration.getPort());
        ChannelFuture channelFuture
                = serverBootstrap.bind(new InetSocketAddress(configuration.getHost(), configuration.getPort())).sync();
        channel = channelFuture.channel();
        // to keep track of all channels in use
        allChannels.add(channel);
    }

};