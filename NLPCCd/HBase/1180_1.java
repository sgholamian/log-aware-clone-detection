//,temp,sample_3906.java,2,10,temp,sample_3904.java,2,9
//,3
public class xxx {
protected void initReconfigurable(Configuration confToLoad) {
this.allowFallbackToSimpleAuth = confToLoad.getBoolean(FALLBACK_TO_INSECURE_CLIENT_AUTH, false);
if (isSecurityEnabled && allowFallbackToSimpleAuth) {
LOG.warn("(" + FALLBACK_TO_INSECURE_CLIENT_AUTH + " = true).");


log.info("impersonation is possible");
}
}

};