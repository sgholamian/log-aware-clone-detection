//,temp,RemoteSASKeyGeneratorImpl.java,207,246,temp,RemoteSASKeyGeneratorImpl.java,169,205
//,3
public class xxx {
  @Override
  public URI getContainerSASUri(String storageAccount,
      String container) throws SASKeyGenerationException {
    RemoteSASKeyGenerationResponse sasKeyResponse = null;
    try {
      CachedSASKeyEntry cacheKey = new CachedSASKeyEntry(storageAccount, container, "/");
      URI cacheResult = cache.get(cacheKey);
      if (cacheResult != null) {
        return cacheResult;
      }

      LOG.debug("Generating Container SAS Key: Storage Account {}, Container {}", storageAccount, container);
      URIBuilder uriBuilder = new URIBuilder();
      uriBuilder.setPath("/" + CONTAINER_SAS_OP);
      uriBuilder.addParameter(STORAGE_ACCOUNT_QUERY_PARAM_NAME, storageAccount);
      uriBuilder.addParameter(CONTAINER_QUERY_PARAM_NAME, container);
      uriBuilder.addParameter(SAS_EXPIRY_QUERY_PARAM_NAME,
          "" + getSasKeyExpiryPeriod());

      sasKeyResponse = makeRemoteRequest(commaSeparatedUrls, uriBuilder.getPath(),
              uriBuilder.getQueryParams());

      if (sasKeyResponse.getResponseCode() == REMOTE_CALL_SUCCESS_CODE) {
        URI sasKey = new URI(sasKeyResponse.getSasKey());
        cache.put(cacheKey, sasKey);
        return sasKey;
      } else {
        throw new SASKeyGenerationException(
            "Remote Service encountered error in SAS Key generation : "
                + sasKeyResponse.getResponseMessage());
      }
    } catch (URISyntaxException uriSyntaxEx) {
      throw new SASKeyGenerationException("Encountered URISyntaxException"
          + " while building the HttpGetRequest to remote service for ",
          uriSyntaxEx);
    }
  }

};