//,temp,RemoteWasbAuthorizerImpl.java,117,147,temp,RemoteSASKeyGeneratorImpl.java,130,167
//,3
public class xxx {
  @Override
  public void init(Configuration conf)
      throws IOException {
    LOG.debug("Initializing RemoteWasbAuthorizerImpl instance");
    this.isKerberosSupportEnabled =
        conf.getBoolean(Constants.AZURE_KERBEROS_SUPPORT_PROPERTY_NAME, false);
    this.isSpnegoTokenCacheEnabled =
        conf.getBoolean(Constants.AZURE_ENABLE_SPNEGO_TOKEN_CACHE, true);
    this.commaSeparatedUrls =
        conf.getTrimmedStrings(KEY_REMOTE_AUTH_SERVICE_URLS);
    if (this.commaSeparatedUrls == null
        || this.commaSeparatedUrls.length <= 0) {
      throw new IOException(KEY_REMOTE_AUTH_SERVICE_URLS + " config not set"
          + " in configuration.");
    }
    this.retryPolicy = RetryUtils.getMultipleLinearRandomRetry(conf,
        AUTHORIZER_HTTP_CLIENT_RETRY_POLICY_ENABLED_KEY, true,
        AUTHORIZER_HTTP_CLIENT_RETRY_POLICY_SPEC_SPEC,
        AUTHORIZER_HTTP_CLIENT_RETRY_POLICY_SPEC_DEFAULT);
    if (isKerberosSupportEnabled && UserGroupInformation.isSecurityEnabled()) {
      this.remoteCallHelper = new SecureWasbRemoteCallHelper(retryPolicy, false,
          isSpnegoTokenCacheEnabled);
    } else {
      this.remoteCallHelper = new WasbRemoteCallHelper(retryPolicy);
    }

    this.cache = new CachingAuthorizer<>(
        conf.getTimeDuration(AUTHORIZATION_CACHEENTRY_EXPIRY_PERIOD, 5L, TimeUnit.MINUTES), "AUTHORIZATION"
    );
    this.cache.init(conf);
  }

};