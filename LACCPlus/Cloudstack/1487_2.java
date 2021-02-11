//,temp,SAML2LoginAPIAuthenticatorCmd.java,359,369,temp,ListAndSwitchSAMLAccountCmd.java,196,206
//,2
public class xxx {
    @Override
    public void setAuthenticators(List<PluggableAPIAuthenticator> authenticators) {
        for (PluggableAPIAuthenticator authManager: authenticators) {
            if (authManager != null && authManager instanceof SAML2AuthManager) {
                _samlAuthManager = (SAML2AuthManager) authManager;
            }
        }
        if (_samlAuthManager == null) {
            s_logger.error("No suitable Pluggable Authentication Manager found for SAML2 listAndSwitchSamlAccount Cmd");
        }
    }

};