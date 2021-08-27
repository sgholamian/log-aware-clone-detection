//,temp,BaseSSLContextParameters.java,252,263,temp,SSLContextClientParameters.java,62,71
//,3
public class xxx {
    protected void configureSSLContext(SSLContext context) throws GeneralSecurityException {
        LOG.trace("Configuring client and server side SSLContext parameters on SSLContext [{}]...", context);

        if (this.getSessionTimeout() != null) {
            LOG.debug("Configuring client and server side SSLContext session timeout on SSLContext [{}] to [{}]",
                    context, this.getSessionTimeout());
            this.configureSessionContext(context.getClientSessionContext(), this.getSessionTimeout());
            this.configureSessionContext(context.getServerSessionContext(), this.getSessionTimeout());
        }

        LOG.trace("Configured client and server side SSLContext parameters on SSLContext [{}].", context);
    }

};