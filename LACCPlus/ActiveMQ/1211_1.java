//,temp,NettyTransportSupport.java,177,200,temp,NettyTransportSupport.java,152,175
//,2
public class xxx {
    private static String[] buildEnabledCipherSuites(SSLEngine engine, NettyTransportSslOptions options) {
        List<String> enabledCipherSuites = new ArrayList<String>();

        if (options.getEnabledCipherSuites() != null) {
            List<String> configuredCipherSuites = Arrays.asList(options.getEnabledCipherSuites());
            LOG.trace("Configured cipher suites from transport options: {}", configuredCipherSuites);
            enabledCipherSuites.addAll(configuredCipherSuites);
        } else {
            List<String> engineCipherSuites = Arrays.asList(engine.getEnabledCipherSuites());
            LOG.trace("Default cipher suites from the SSLEngine: {}", engineCipherSuites);
            enabledCipherSuites.addAll(engineCipherSuites);
        }

        String[] disabledCipherSuites = options.getDisabledCipherSuites();
        if (disabledCipherSuites != null) {
            List<String> disabled = Arrays.asList(disabledCipherSuites);
            LOG.trace("Disabled cipher suites: {}", disabled);
            enabledCipherSuites.removeAll(disabled);
        }

        LOG.trace("Enabled cipher suites: {}", enabledCipherSuites);

        return enabledCipherSuites.toArray(new String[0]);
    }

};