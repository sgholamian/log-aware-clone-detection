//,temp,sample_458.java,2,16,temp,sample_459.java,2,16
//,2
public class xxx {
public void dummy_method(){
_clientConnection.configureBlocking(false);
final SSLContext sslContext = Link.initSSLContext(true);
SSLEngine sslEngine = sslContext.createSSLEngine(_host, _port);
sslEngine.setUseClientMode(true);
sslEngine.setEnabledProtocols(SSLUtils.getSupportedProtocols(sslEngine.getEnabledProtocols()));
sslEngine.beginHandshake();
if (!Link.doHandshake(_clientConnection, sslEngine, true)) {
_selector.close();
throw new IOException("SSL Handshake failed while connecting to host: " + _host + " port: " + _port);
}


log.info("ssl handshake done");
}

};