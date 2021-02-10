//,temp,RemoteWasbAuthorizerImpl.java,117,147,temp,RemoteSASKeyGeneratorImpl.java,130,167
//,3
public class xxx {
  public void initialize(Configuration conf) throws IOException {

    LOG.debug("Initializing RemoteSASKeyGeneratorImpl instance");

    this.retryPolicy = RetryUtils.getMultipleLinearRandomRetry(conf,
        SAS_KEY_GENERATOR_HTTP_CLIENT_RETRY_POLICY_ENABLED_KEY, true,
        SAS_KEY_GENERATOR_HTTP_CLIENT_RETRY_POLICY_SPEC_KEY,
        SAS_KEY_GENERATOR_HTTP_CLIENT_RETRY_POLICY_SPEC_DEFAULT);

    this.isKerberosSupportEnabled =
        conf.getBoolean(Constants.AZURE_KERBEROS_SUPPORT_PROPERTY_NAME, false);
    this.isSpnegoTokenCacheEnabled =
        conf.getBoolean(Constants.AZURE_ENABLE_SPNEGO_TOKEN_CACHE, true);
    this.commaSeparatedUrls = conf.getTrimmedStrings(KEY_CRED_SERVICE_URLS);
    if (this.commaSeparatedUrls == null || this.commaSeparatedUrls.length <= 0) {
      throw new IOException(
          KEY_CRED_SERVICE_URLS + " config not set" + " in configuration.");
    }
    if (isKerberosSupportEnabled && UserGroupInformation.isSecurityEnabled()) {
      this.remoteCallHelper = new SecureWasbRemoteCallHelper(retryPolicy, false,
          isSpnegoTokenCacheEnabled);
    } else {
      this.remoteCallHelper = new WasbRemoteCallHelper(retryPolicy);
    }

    /* Expire the cache entry five minutes before the actual saskey expiry, so that we never encounter a case
     * where a stale sas-key-entry is picked up from the cache; which is expired on use.
     */
    long sasKeyExpiryPeriodInMinutes = getSasKeyExpiryPeriod() * HOURS_IN_DAY * MINUTES_IN_HOUR; // sas-expiry is in days, convert into mins
    long cacheEntryDurationInMinutes =
        conf.getTimeDuration(SASKEY_CACHEENTRY_EXPIRY_PERIOD, sasKeyExpiryPeriodInMinutes, TimeUnit.MINUTES);
    cacheEntryDurationInMinutes = (cacheEntryDurationInMinutes > (sasKeyExpiryPeriodInMinutes - 5))
        ? (sasKeyExpiryPeriodInMinutes - 5)
        : cacheEntryDurationInMinutes;
    this.cache = new CachingAuthorizer<>(cacheEntryDurationInMinutes, "SASKEY");
    this.cache.init(conf);
    LOG.debug("Initialization of RemoteSASKeyGenerator instance successful");
  }

};