//,temp,BaseSSLContextParameters.java,561,645,temp,BaseSSLContextParameters.java,460,549
//,3
public class xxx {
    protected List<Configurer<SSLSocket>> getSSLSocketFactorySSLSocketConfigurers(SSLContext context) {
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

        Configurer<SSLSocket> sslSocketConfigurer = new Configurer<SSLSocket>() {

            @Override
            public SSLSocket configure(SSLSocket socket) {

                if (!getSNIHostNames().isEmpty()) {
                    SSLParameters sslParameters = socket.getSSLParameters();
                    sslParameters.setServerNames(getSNIHostNames());
                    socket.setSSLParameters(sslParameters);
                }

                Collection<String> filteredCipherSuites = BaseSSLContextParameters.this
                        .filter(enabledCipherSuites, Arrays.asList(socket.getSSLParameters().getCipherSuites()),
                                Arrays.asList(socket.getEnabledCipherSuites()),
                                enabledCipherSuitePatterns, defaultEnabledCipherSuitePatterns,
                                !allowPassthrough);
                if (LOG.isDebugEnabled()) {
                    LOG.debug(SSL_SOCKET_CIPHER_SUITE_LOG_MSG,
                            socket,
                            enabledCipherSuites,
                            enabledCipherSuitePatterns,
                            socket.getSSLParameters().getCipherSuites(),
                            socket.getEnabledCipherSuites(),
                            defaultEnabledCipherSuitePatterns,
                            filteredCipherSuites);
                }

                socket.setEnabledCipherSuites(filteredCipherSuites.toArray(new String[filteredCipherSuites.size()]));

                Collection<String> filteredSecureSocketProtocols = BaseSSLContextParameters.this
                        .filter(enabledSecureSocketProtocols, Arrays.asList(socket.getSSLParameters().getProtocols()),
                                Arrays.asList(socket.getEnabledProtocols()),
                                enabledSecureSocketProtocolsPatterns, defaultEnabledSecureSocketProtocolsPatterns,
                                !allowPassthrough);

                if (LOG.isDebugEnabled()) {
                    LOG.debug(SSL_SOCKET_PROTOCOL_LOG_MSG,
                            socket,
                            enabledSecureSocketProtocols,
                            enabledSecureSocketProtocolsPatterns,
                            socket.getSSLParameters().getProtocols(),
                            socket.getEnabledProtocols(),
                            defaultEnabledSecureSocketProtocolsPatterns,
                            filteredSecureSocketProtocols);
                }

                socket.setEnabledProtocols(
                        filteredSecureSocketProtocols.toArray(new String[filteredSecureSocketProtocols.size()]));
                return socket;
            }
        };

        List<Configurer<SSLSocket>> sslSocketConfigurers = new LinkedList<>();
        sslSocketConfigurers.add(sslSocketConfigurer);

        return sslSocketConfigurers;
    }

};