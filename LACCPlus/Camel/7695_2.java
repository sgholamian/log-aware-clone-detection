//,temp,BaseSSLContextParameters.java,1080,1090,temp,BaseSSLContextParameters.java,999,1009
//,2
public class xxx {
        private ServerSocket configureSocket(ServerSocket s) {
            SSLServerSocket workingSocket = (SSLServerSocket) s;

            LOG.debug("Created ServerSocket [{}] from SslServerSocketFactory [{}].", s, sslServerSocketFactory);

            for (Configurer<SSLServerSocket> configurer : this.sslServerSocketConfigurers) {
                workingSocket = configurer.configure(workingSocket);
            }

            return workingSocket;
        }

};