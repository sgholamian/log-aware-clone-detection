//,temp,BaseSSLContextParameters.java,596,638,temp,BaseSSLContextParameters.java,495,542
//,3
public class xxx {
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