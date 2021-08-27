//,temp,MinaConsumer.java,172,204,temp,MinaProducer.java,368,400
//,3
public class xxx {
    protected void setupDatagramProtocol(String uri) {
        boolean minaLogger = configuration.isMinaLogger();
        boolean transferExchange = configuration.isTransferExchange();
        List<IoFilter> filters = configuration.getFilters();

        if (transferExchange) {
            throw new IllegalArgumentException("transferExchange=true is not supported for datagram protocol");
        }

        address = new InetSocketAddress(configuration.getHost(), configuration.getPort());
        final int processorCount = Runtime.getRuntime().availableProcessors() + 1;
        connector = new NioDatagramConnector(processorCount);

        if (configuration.isOrderedThreadPoolExecutor()) {
            workerPool = new OrderedThreadPoolExecutor(configuration.getMaximumPoolSize());
        } else {
            workerPool = new UnorderedThreadPoolExecutor(configuration.getMaximumPoolSize());
        }
        connectorConfig = connector.getSessionConfig();
        connector.getFilterChain().addLast("threadPool", new ExecutorFilter(workerPool));
        if (minaLogger) {
            connector.getFilterChain().addLast("logger", new LoggingFilter());
        }
        appendIoFiltersToChain(filters, connector.getFilterChain());
        if (configuration.getSslContextParameters() != null) {
            LOG.warn("Using datagram protocol, {}, but an SSLContextParameters instance was provided. "
                     + "SSLContextParameters is only supported on the TCP protocol.",
                    configuration.getProtocol());
        }
        configureDataGramCodecFactory("MinaProducer", connector, configuration);
        // set connect timeout to mina in seconds
        connector.setConnectTimeoutMillis(timeout);
    }

};