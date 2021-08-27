//,temp,SSLContextServerParameters.java,125,162,temp,SSLContextServerParameters.java,83,123
//,3
public class xxx {
    @Override
    protected List<Configurer<SSLServerSocket>> getSSLServerSocketFactorySSLServerSocketConfigurers(SSLContext context) {
        List<Configurer<SSLServerSocket>> sslServerSocketConfigurers
                = super.getSSLServerSocketFactorySSLServerSocketConfigurers(context);

        if (this.getClientAuthentication() != null) {

            final ClientAuthentication clientAuthValue
                    = ClientAuthentication.valueOf(this.parsePropertyValue(this.getClientAuthentication()));

            Configurer<SSLServerSocket> sslServerSocketConfigurer = new Configurer<SSLServerSocket>() {
                @Override
                public SSLServerSocket configure(SSLServerSocket socket) {
                    LOG.trace("Configuring client-auth on SSLServerSocket [{}] to [{}].", socket, clientAuthValue);
                    switch (clientAuthValue) {
                        case NONE:
                            socket.setWantClientAuth(false);
                            socket.setNeedClientAuth(false);
                            break;
                        case WANT:
                            socket.setWantClientAuth(true);
                            break;
                        case REQUIRE:
                            socket.setNeedClientAuth(true);
                            break;
                        default:
                            throw new RuntimeCamelException("Unknown ClientAuthentication value: " + clientAuthValue);
                    }

                    return socket;
                }
            };

            sslServerSocketConfigurers.add(sslServerSocketConfigurer);
        }

        return sslServerSocketConfigurers;
    }

};