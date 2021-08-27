//,temp,BaseSSLContextParameters.java,1080,1090,temp,BaseSSLContextParameters.java,999,1009
//,2
public class xxx {
        private Socket configureSocket(Socket s) {
            SSLSocket workingSocket = (SSLSocket) s;

            LOG.debug("Created Socket [{}] from SocketFactory [{}].", s, sslSocketFactory);

            for (Configurer<SSLSocket> configurer : this.sslSocketConfigurers) {
                workingSocket = configurer.configure(workingSocket);
            }

            return workingSocket;
        }

};