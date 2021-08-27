//,temp,SSLContextServerParameters.java,136,155,temp,SSLContextServerParameters.java,97,116
//,2
public class xxx {
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