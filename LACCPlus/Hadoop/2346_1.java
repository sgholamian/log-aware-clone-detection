//,temp,RemoteSASKeyGeneratorImpl.java,207,246,temp,RemoteSASKeyGeneratorImpl.java,169,205
//,3
public class xxx {
  @Override
  public URI getRelativeBlobSASUri(String storageAccount,
      String container, String relativePath) throws SASKeyGenerationException {

    try {
      CachedSASKeyEntry cacheKey = new CachedSASKeyEntry(storageAccount, container, relativePath);
      URI cacheResult = cache.get(cacheKey);
      if (cacheResult != null) {
        return cacheResult;
      }

      LOG.debug("Generating RelativePath SAS Key for relativePath {} inside Container {} inside Storage Account {}",
          relativePath, container, storageAccount);

      URIBuilder uriBuilder = new URIBuilder();
      uriBuilder.setPath("/" + BLOB_SAS_OP);
      uriBuilder.addParameter(STORAGE_ACCOUNT_QUERY_PARAM_NAME, storageAccount);
      uriBuilder.addParameter(CONTAINER_QUERY_PARAM_NAME, container);
      uriBuilder.addParameter(RELATIVE_PATH_QUERY_PARAM_NAME, relativePath);
      uriBuilder.addParameter(SAS_EXPIRY_QUERY_PARAM_NAME,
          "" + getSasKeyExpiryPeriod());

      RemoteSASKeyGenerationResponse sasKeyResponse =
          makeRemoteRequest(commaSeparatedUrls, uriBuilder.getPath(),
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
          + " while building the HttpGetRequest to " + " remote service",
          uriSyntaxEx);
    }
  }

};