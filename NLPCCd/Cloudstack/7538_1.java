//,temp,sample_2346.java,2,13,temp,sample_3712.java,2,13
//,2
public class xxx {
public void setAuthenticators(List<PluggableAPIAuthenticator> authenticators) {
for (PluggableAPIAuthenticator authManager: authenticators) {
if (authManager != null && authManager instanceof SAML2AuthManager) {
_samlAuthManager = (SAML2AuthManager) authManager;
}
}
if (_samlAuthManager == null) {


log.info("no suitable pluggable authentication manager found for listandswitchsamlaccount cmd");
}
}

};