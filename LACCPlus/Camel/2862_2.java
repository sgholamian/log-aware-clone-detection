//,temp,SSLContextServerParameters.java,136,155,temp,SSLContextServerParameters.java,97,116
//,2
public class xxx {
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