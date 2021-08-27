//,temp,sample_1278.java,2,12,temp,sample_1274.java,2,12
//,3
public class xxx {
protected List<Configurer<SSLEngine>> getSSLEngineConfigurers(SSLContext context) {
List<Configurer<SSLEngine>> configurers = super.getSSLEngineConfigurers(context);
if (this.getClientParameters() != null) {
configurers.addAll(this.getClientParameters().getSSLEngineConfigurers(context));
}
if (this.getServerParameters() != null) {


log.info("augmenting sslengine configurers with configurers from server parameters on sslcontext");
}
}

};