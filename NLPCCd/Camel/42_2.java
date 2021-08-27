//,temp,sample_1278.java,2,12,temp,sample_1274.java,2,12
//,3
public class xxx {
protected void configureSSLContext(SSLContext context) throws GeneralSecurityException {
super.configureSSLContext(context);
if (this.getClientParameters() != null) {
this.getClientParameters().configureSSLContext(context);
}
if (this.getServerParameters() != null) {


log.info("overriding server side sslcontext parameters on sslcontext with configured server parameters");
}
}

};