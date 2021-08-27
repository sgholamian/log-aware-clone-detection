//,temp,BaseSSLContextParameters.java,561,645,temp,BaseSSLContextParameters.java,460,549
//,3
public class xxx {
    protected List<Configurer<SSLServerSocket>> getSSLServerSocketFactorySSLServerSocketConfigurers(SSLContext context) {
        final List<String> enabledCipherSuites = this.getCipherSuites() == null
                ? null : this.parsePropertyValues(this.getCipherSuites().getCipherSuite());

        final Patterns enabledCipherSuitePatterns;
        final Patterns defaultEnabledCipherSuitePatterns = this.getDefaultCipherSuitesFilter().getPatterns();

        if (this.getCipherSuitesFilter() != null) {
            enabledCipherSuitePatterns = this.getCipherSuitesFilter().getPatterns();
        } else {
            enabledCipherSuitePatterns = null;
        }

        ///

        final List<String> enabledSecureSocketProtocols = this.getSecureSocketProtocols() == null
                ? null : this.parsePropertyValues(this.getSecureSocketProtocols().getSecureSocketProtocol());

        final Patterns enabledSecureSocketProtocolsPatterns;
        final Patterns defaultEnabledSecureSocketProtocolsPatterns = this.getDefaultSecureSocketProcotolFilter().getPatterns();

        if (this.getSecureSocketProtocolsFilter() != null) {
            enabledSecureSocketProtocolsPatterns = this.getSecureSocketProtocolsFilter().getPatterns();
        } else {
            enabledSecureSocketProtocolsPatterns = null;
        }

        //

        final boolean allowPassthrough = getAllowPassthrough();

        //////

        Configurer<SSLServerSocket> sslServerSocketConfigurer = new Configurer<SSLServerSocket>() {

            @Override
            public SSLServerSocket configure(SSLServerSocket socket) {

                Collection<String> filteredCipherSuites = BaseSSLContextParameters.this
                        .filter(enabledCipherSuites, Arrays.asList(socket.getSupportedCipherSuites()),
                                Arrays.asList(socket.getEnabledCipherSuites()),
                                enabledCipherSuitePatterns, defaultEnabledCipherSuitePatterns,
                                !allowPassthrough);

                if (LOG.isDebugEnabled()) {
                    LOG.debug(SSL_SERVER_SOCKET_CIPHER_SUITE_LOG_MSG,
                            socket,
                            enabledCipherSuites,
                            enabledCipherSuitePatterns,
                            socket.getSupportedCipherSuites(),
                            socket.getEnabledCipherSuites(),
                            defaultEnabledCipherSuitePatterns,
                            filteredCipherSuites);
                }

                socket.setEnabledCipherSuites(filteredCipherSuites.toArray(new String[filteredCipherSuites.size()]));

                Collection<String> filteredSecureSocketProtocols = BaseSSLContextParameters.this
                        .filter(enabledSecureSocketProtocols, Arrays.asList(socket.getSupportedProtocols()),
                                Arrays.asList(socket.getEnabledProtocols()),
                                enabledSecureSocketProtocolsPatterns, defaultEnabledSecureSocketProtocolsPatterns,
                                !allowPassthrough);

                if (LOG.isDebugEnabled()) {
                    LOG.debug(SSL_SERVER_SOCKET_PROTOCOL_LOG_MSG,
                            socket,
                            enabledSecureSocketProtocols,
                            enabledSecureSocketProtocolsPatterns,
                            socket.getSupportedProtocols(),
                            socket.getEnabledProtocols(),
                            defaultEnabledSecureSocketProtocolsPatterns,
                            filteredSecureSocketProtocols);
                }

                socket.setEnabledProtocols(
                        filteredSecureSocketProtocols.toArray(new String[filteredSecureSocketProtocols.size()]));
                return socket;
            }
        };

        List<Configurer<SSLServerSocket>> sslServerSocketConfigurers = new LinkedList<>();
        sslServerSocketConfigurers.add(sslServerSocketConfigurer);

        return sslServerSocketConfigurers;
    }

};