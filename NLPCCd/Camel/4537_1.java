//,temp,sample_1277.java,2,9,temp,sample_1281.java,2,9
//,2
public class xxx {
protected List<Configurer<SSLEngine>> getSSLEngineConfigurers(SSLContext context) {
List<Configurer<SSLEngine>> configurers = super.getSSLEngineConfigurers(context);
if (this.getClientParameters() != null) {


log.info("augmenting sslengine configurers with configurers from client parameters on sslcontext");
}
}

};