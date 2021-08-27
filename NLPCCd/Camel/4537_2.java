//,temp,sample_1277.java,2,9,temp,sample_1281.java,2,9
//,2
public class xxx {
protected List<Configurer<SSLSocketFactory>> getSSLSocketFactoryConfigurers(SSLContext context) {
List<Configurer<SSLSocketFactory>> configurers = super.getSSLSocketFactoryConfigurers(context);
if (this.getClientParameters() != null) {


log.info("augmenting sslsocketfactory configurers with configurers from client parameters on sslcontext");
}
}

};