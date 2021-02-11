//,temp,NeutronRestApi.java,70,82,temp,BigSwitchBcfApi.java,114,124
//,3
public class xxx {
    public BigSwitchBcfApi() {
        _client = createHttpClient();
        _client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

        try {
            // Cast to ProtocolSocketFactory to avoid the deprecated constructor with the SecureProtocolSocketFactory parameter
            Protocol.registerProtocol("https", new Protocol("https", (ProtocolSocketFactory) new TrustingProtocolSocketFactory(), _port));
        } catch (IOException e) {
            S_LOGGER.warn("Failed to register the TrustingProtocolSocketFactory, falling back to default SSLSocketFactory", e);
        }
    }

};