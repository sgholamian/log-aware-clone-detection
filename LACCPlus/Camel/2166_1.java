//,temp,BaseSSLContextParameters.java,596,638,temp,BaseSSLContextParameters.java,495,542
//,3
public class xxx {
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