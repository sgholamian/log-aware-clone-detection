//,temp,ConsoleProxyBaseServerFactoryImpl.java,42,48,temp,VmwareResource.java,5100,5106
//,3
public class xxx {
    @Override
    public SSLServerSocket createSSLServerSocket(int port) throws IOException {
        if (s_logger.isInfoEnabled())
            s_logger.info("SSL server socket is not supported in ConsoleProxyBaseServerFactoryImpl");

        return null;
    }

};