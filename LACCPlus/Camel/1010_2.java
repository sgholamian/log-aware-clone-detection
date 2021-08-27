//,temp,MinaConsumer.java,152,170,temp,MinaProducer.java,284,301
//,3
public class xxx {
    protected void setupVmProtocol(String uri) {
        boolean minaLogger = configuration.isMinaLogger();
        List<IoFilter> filters = configuration.getFilters();

        address = new VmPipeAddress(configuration.getPort());
        connector = new VmPipeConnector();

        // connector config
        if (minaLogger) {
            connector.getFilterChain().addLast("logger", new LoggingFilter());
        }
        appendIoFiltersToChain(filters, connector.getFilterChain());
        if (configuration.getSslContextParameters() != null) {
            LOG.warn("Using vm protocol"
                     + ", but an SSLContextParameters instance was provided.  SSLContextParameters is only supported on the TCP protocol.");
        }
        configureCodecFactory("MinaProducer", connector);
    }

};