//,temp,TcpFaultyTransportFactory.java,48,64,temp,NIOSSLTransportFactory.java,89,107
//,3
public class xxx {
    @Override
    protected Transport createTransport(URI location, WireFormat wf) throws UnknownHostException, IOException {

        URI localLocation = null;
        String path = location.getPath();
        // see if the path is a local URI location
        if (path != null && path.length() > 0) {
            int localPortIndex = path.indexOf(':');
            try {
                Integer.parseInt(path.substring(localPortIndex + 1, path.length()));
                String localString = location.getScheme() + ":/" + path;
                localLocation = new URI(localString);
            } catch (Exception e) {
                LOG.warn("path isn't a valid local location for SslTransport to use", e);
            }
        }
        SocketFactory socketFactory = createSocketFactory();
        return new SslTransport(wf, (SSLSocketFactory) socketFactory, location, localLocation, false);
    }

};