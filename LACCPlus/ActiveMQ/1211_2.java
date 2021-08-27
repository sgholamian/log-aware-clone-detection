//,temp,NettyTransportSupport.java,177,200,temp,NettyTransportSupport.java,152,175
//,2
public class xxx {
    private static String[] buildEnabledProtocols(SSLEngine engine, NettyTransportSslOptions options) {
        List<String> enabledProtocols = new ArrayList<String>();

        if (options.getEnabledProtocols() != null) {
            List<String> configuredProtocols = Arrays.asList(options.getEnabledProtocols());
            LOG.trace("Configured protocols from transport options: {}", configuredProtocols);
            enabledProtocols.addAll(configuredProtocols);
        } else {
            List<String> engineProtocols = Arrays.asList(engine.getEnabledProtocols());
            LOG.trace("Default protocols from the SSLEngine: {}", engineProtocols);
            enabledProtocols.addAll(engineProtocols);
        }

        String[] disabledProtocols = options.getDisabledProtocols();
        if (disabledProtocols != null) {
            List<String> disabled = Arrays.asList(disabledProtocols);
            LOG.trace("Disabled protocols: {}", disabled);
            enabledProtocols.removeAll(disabled);
        }

        LOG.trace("Enabled protocols: {}", enabledProtocols);

        return enabledProtocols.toArray(new String[0]);
    }

};