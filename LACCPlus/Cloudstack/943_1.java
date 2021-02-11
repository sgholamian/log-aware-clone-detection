//,temp,NeutronRestApi.java,70,82,temp,BigSwitchBcfApi.java,114,124
//,3
public class xxx {
    protected NeutronRestApi(final Class<? extends HttpMethodBase> httpClazz, final String protocol, final int port) {
        client = createHttpClient();
        client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        this.httpClazz = httpClazz;

        try {
            // Cast to ProtocolSocketFactory to avoid the deprecated constructor
            // with the SecureProtocolSocketFactory parameter
            Protocol.registerProtocol(protocol, new Protocol(protocol, (ProtocolSocketFactory) new TrustingProtocolSocketFactory(), HTTPS_PORT));
        } catch (IOException e) {
            s_logger.warn("Failed to register the TrustingProtocolSocketFactory, falling back to default SSLSocketFactory", e);
        }
    }

};