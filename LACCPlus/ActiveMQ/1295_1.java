//,temp,AmqpTransportFilter.java,159,171,temp,MQTTTransportFilter.java,167,180
//,2
public class xxx {
    @Override
    public X509Certificate[] getPeerCertificates() {
        X509Certificate[] peerCerts = null;
        if (next instanceof SslTransport) {
            peerCerts = ((SslTransport) next).getPeerCertificates();
        } else if (next instanceof NIOSSLTransport) {
            peerCerts = ((NIOSSLTransport) next).getPeerCertificates();
        }
        if (trace && peerCerts != null) {
            LOG.debug("Peer Identity has been verified\n");
        }
        return peerCerts;
    }

};