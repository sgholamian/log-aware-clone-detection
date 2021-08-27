//,temp,SslTransportFactory.java,106,123,temp,TcpTransportFactory.java,124,144
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
                LOG.warn("path isn't a valid local location for TcpTransport to use: {}", e.getMessage());
                if(LOG.isDebugEnabled()) {
                    LOG.debug("Failure detail", e);
                }
            }
        }
        SocketFactory socketFactory = createSocketFactory();
        return createTcpTransport(wf, socketFactory, location, localLocation);
    }

};