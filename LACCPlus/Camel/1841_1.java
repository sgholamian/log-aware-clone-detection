//,temp,MinaConsumer.java,172,204,temp,MinaProducer.java,368,400
//,3
public class xxx {
    protected void setupSocketProtocol(String uri, MinaConfiguration configuration) throws Exception {
        LOG.debug("createSocketEndpoint");
        boolean minaLogger = configuration.isMinaLogger();
        List<IoFilter> filters = configuration.getFilters();

        address = new InetSocketAddress(configuration.getHost(), configuration.getPort());

        final int processorCount = Runtime.getRuntime().availableProcessors() + 1;
        acceptor = new NioSocketAcceptor(processorCount);

        // acceptor connectorConfig
        configureCodecFactory("MinaConsumer", acceptor, configuration);
        ((NioSocketAcceptor) acceptor).setReuseAddress(true);
        acceptor.setCloseOnDeactivation(true);

        if (configuration.isOrderedThreadPoolExecutor()) {
            workerPool = new OrderedThreadPoolExecutor(configuration.getMaximumPoolSize());
        } else {
            workerPool = new UnorderedThreadPoolExecutor(configuration.getMaximumPoolSize());
        }
        acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(workerPool));
        if (minaLogger) {
            acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        }
        appendIoFiltersToChain(filters, acceptor.getFilterChain());
        if (configuration.getSslContextParameters() != null) {
            SslFilter filter = new SslFilter(
                    configuration.getSslContextParameters().createSSLContext(getEndpoint().getCamelContext()),
                    configuration.isAutoStartTls());
            filter.setUseClientMode(false);
            acceptor.getFilterChain().addFirst("sslFilter", filter);
        }
    }

};