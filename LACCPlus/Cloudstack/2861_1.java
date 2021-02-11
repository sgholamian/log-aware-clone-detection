//,temp,SAML2LoginAPIAuthenticatorCmd.java,359,369,temp,GetServiceProviderMetaDataCmd.java,274,284
//,2
public class xxx {
    @Override
    public void setAuthenticators(List<PluggableAPIAuthenticator> authenticators) {
        for (PluggableAPIAuthenticator authManager: authenticators) {
            if (authManager != null && authManager instanceof SAML2AuthManager) {
                samlAuthManager = (SAML2AuthManager) authManager;
            }
        }
        if (samlAuthManager == null) {
            s_logger.error("No suitable Pluggable Authentication Manager found for SAML2 Login Cmd");
        }
    }

};