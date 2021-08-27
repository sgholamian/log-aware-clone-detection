//,temp,SingleTCPNettyServerBootstrapFactory.java,121,188,temp,SingleUDPNettyServerBootstrapFactory.java,119,197
//,3
public class xxx {
    protected void startServerBootstrap() throws Exception {
        // create non-shared worker pool
        EventLoopGroup wg = configuration.getWorkerGroup();
        if (wg == null) {
            // create new pool which we should shutdown when stopping as its not shared
            workerGroup = new NettyWorkerPoolBuilder()
                    .withNativeTransport(configuration.isNativeTransport())
                    .withWorkerCount(configuration.getWorkerCount())
                    .withName("NettyServerTCPWorker")
                    .build();
            wg = workerGroup;
        }

        Bootstrap bootstrap = new Bootstrap();
        if (configuration.isNativeTransport()) {
            bootstrap.group(wg).channel(EpollDatagramChannel.class);
        } else {
            bootstrap.group(wg).channel(NioDatagramChannel.class);
        }
        // We cannot set the child option here
        bootstrap.option(ChannelOption.SO_REUSEADDR, configuration.isReuseAddress());
        bootstrap.option(ChannelOption.SO_SNDBUF, configuration.getSendBufferSize());
        bootstrap.option(ChannelOption.SO_RCVBUF, configuration.getReceiveBufferSize());
        bootstrap.option(ChannelOption.SO_BROADCAST, configuration.isBroadcast());
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, configuration.getConnectTimeout());

        // only set this if user has specified
        if (configuration.getReceiveBufferSizePredictor() > 0) {
            bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR,
                    new FixedRecvByteBufAllocator(configuration.getReceiveBufferSizePredictor()));
        }

        if (configuration.getBacklog() > 0) {
            bootstrap.option(ChannelOption.SO_BACKLOG, configuration.getBacklog());
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
                    bootstrap.option(option, o);
                } else {
                    bootstrap.option(option, value);
                }
            }
        }
        LOG.debug("Created Bootstrap {}", bootstrap);

        // set the pipeline factory, which creates the pipeline for each newly created channels
        bootstrap.handler(pipelineFactory);

        InetSocketAddress hostAddress = new InetSocketAddress(configuration.getHost(), configuration.getPort());
        SubnetUtils multicastSubnet = new SubnetUtils(MULTICAST_SUBNET);

        if (multicastSubnet.getInfo().isInRange(configuration.getHost())) {
            ChannelFuture channelFuture = bootstrap.bind(configuration.getPort()).sync();
            channel = channelFuture.channel();
            DatagramChannel datagramChannel = (DatagramChannel) channel;
            String networkInterface
                    = configuration.getNetworkInterface() == null ? LOOPBACK_INTERFACE : configuration.getNetworkInterface();
            multicastNetworkInterface = NetworkInterface.getByName(networkInterface);
            ObjectHelper.notNull(multicastNetworkInterface, "No network interface found for '" + networkInterface + "'.");
            LOG.info("ConnectionlessBootstrap joining {}:{} using network interface: {}", configuration.getHost(),
                    configuration.getPort(), multicastNetworkInterface.getName());
            datagramChannel.joinGroup(hostAddress, multicastNetworkInterface).syncUninterruptibly();
            allChannels.add(datagramChannel);
        } else {
            LOG.info("ConnectionlessBootstrap binding to {}:{}", configuration.getHost(), configuration.getPort());
            ChannelFuture channelFuture = bootstrap.bind(hostAddress).sync();
            channel = channelFuture.channel();
            allChannels.add(channel);
        }
    }

};