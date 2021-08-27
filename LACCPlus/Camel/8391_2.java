//,temp,SSLContextParameters.java,328,348,temp,SSLContextParameters.java,308,326
//,3
public class xxx {
    @Override
    protected void configureSSLContext(SSLContext context) throws GeneralSecurityException {
        LOG.trace("Configuring client and server side SSLContext parameters on SSLContext [{}]...", context);
        super.configureSSLContext(context);

        if (this.getClientParameters() != null) {
            LOG.trace("Overriding client-side SSLContext parameters on SSLContext [{}] with configured client parameters.",
                    context);
            this.getClientParameters().configureSSLContext(context);
        }

        if (this.getServerParameters() != null) {
            LOG.trace("Overriding server-side SSLContext parameters on SSLContext [{}] with configured server parameters.",
                    context);
            this.getServerParameters().configureSSLContext(context);
        }

        LOG.trace("Configured client and server side SSLContext parameters on SSLContext [{}].", context);
    }

};