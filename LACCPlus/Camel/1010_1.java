//,temp,MinaConsumer.java,152,170,temp,MinaProducer.java,284,301
//,3
public class xxx {
    protected void setupVmProtocol(String uri, MinaConfiguration configuration) {

        boolean minaLogger = configuration.isMinaLogger();
        List<IoFilter> filters = configuration.getFilters();

        address = new VmPipeAddress(configuration.getPort());
        acceptor = new VmPipeAcceptor();

        // acceptor connectorConfig
        configureCodecFactory("MinaConsumer", acceptor, configuration);
        if (minaLogger) {
            acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        }
        appendIoFiltersToChain(filters, acceptor.getFilterChain());
        if (configuration.getSslContextParameters() != null) {
            LOG.warn("Using vm protocol"
                     + ", but an SSLContextParameters instance was provided.  SSLContextParameters is only supported on the TCP protocol.");
        }
    }

};