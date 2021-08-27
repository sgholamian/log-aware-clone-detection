//,temp,BaseSSLContextParameters.java,252,263,temp,SSLContextClientParameters.java,62,71
//,3
public class xxx {
    @Override
    protected void configureSSLContext(SSLContext context) throws GeneralSecurityException {
        LOG.trace("Configuring client-side SSLContext parameters on SSLContext [{}]...", context);
        if (this.getSessionTimeout() != null) {
            LOG.info("Configuring client-side SSLContext session timeout on SSLContext [{}] to [{}].", context,
                    this.getSessionTimeout());
            this.configureSessionContext(context.getClientSessionContext(), this.getSessionTimeout());
        }
        LOG.trace("Configured client-side SSLContext parameters on SSLContext [{}].", context);
    }

};