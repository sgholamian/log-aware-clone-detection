//,temp,SSLContextParameters.java,328,348,temp,SSLContextParameters.java,308,326
//,3
public class xxx {
    @Override
    protected List<Configurer<SSLEngine>> getSSLEngineConfigurers(SSLContext context) {
        LOG.trace("Collecting client and server side SSLEngine configurers on SSLContext [{}]...", context);
        List<Configurer<SSLEngine>> configurers = super.getSSLEngineConfigurers(context);

        if (this.getClientParameters() != null) {
            LOG.trace("Augmenting SSLEngine configurers with configurers from client parameters on SSLContext [{}].",
                    context);
            configurers.addAll(this.getClientParameters().getSSLEngineConfigurers(context));
        }

        if (this.getServerParameters() != null) {
            LOG.trace("Augmenting SSLEngine configurers with configurers from server parameters on SSLContext [{}].",
                    context);
            configurers.addAll(this.getServerParameters().getSSLEngineConfigurers(context));
        }

        LOG.trace("Collected client and server side SSLEngine configurers on SSLContext [{}].", context);

        return configurers;
    }

};