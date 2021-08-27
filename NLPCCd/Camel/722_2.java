//,temp,sample_4292.java,2,10,temp,sample_7026.java,2,9
//,3
public class xxx {
protected void configureSSLContext(SSLContext context) throws GeneralSecurityException {
if (this.getSessionTimeout() != null) {
this.configureSessionContext(context.getClientSessionContext(), this.getSessionTimeout());
}


log.info("configured client side sslcontext parameters on sslcontext");
}

};