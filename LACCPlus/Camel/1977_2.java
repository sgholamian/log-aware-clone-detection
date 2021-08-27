//,temp,SSLContextServerParameters.java,125,162,temp,SSLContextServerParameters.java,83,123
//,3
public class xxx {
    @Override
    protected List<Configurer<SSLEngine>> getSSLEngineConfigurers(SSLContext context) {
        // NOTE: if the super class gets additional shared configuration options beyond
        // cipher suites and protocols, this method needs to address that.
        // As is, we do NOT pass the configurers along for those two settings.

        List<Configurer<SSLEngine>> sslEngineConfigurers = new LinkedList<>();

        if (this.getClientAuthentication() != null) {

            final ClientAuthentication clientAuthValue
                    = ClientAuthentication.valueOf(this.parsePropertyValue(this.getClientAuthentication()));

            Configurer<SSLEngine> sslEngineConfigurer = new Configurer<SSLEngine>() {
                @Override
                public SSLEngine configure(SSLEngine engine) {
                    LOG.trace("Configuring client-auth on SSLEngine [{}] to [{}].", engine, clientAuthValue);
                    switch (clientAuthValue) {
                        case NONE:
                            engine.setWantClientAuth(false);
                            engine.setNeedClientAuth(false);
                            break;
                        case WANT:
                            engine.setWantClientAuth(true);
                            break;
                        case REQUIRE:
                            engine.setNeedClientAuth(true);
                            break;
                        default:
                            throw new RuntimeCamelException("Unknown ClientAuthentication value: " + clientAuthValue);
                    }

                    return engine;
                }
            };

            sslEngineConfigurers.add(sslEngineConfigurer);
        }

        return sslEngineConfigurers;
    }

};