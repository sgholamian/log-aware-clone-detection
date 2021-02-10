//,temp,RestClient.java,752,788,temp,RestClient.java,351,387
//,3
public class xxx {
  @Override
  public List<OzoneKey> listKeys(String volumeName, String bucketName,
                                 String keyPrefix, String prevKey,
                                 int maxListResult)
      throws IOException {
    try {
      HddsClientUtils.verifyResourceName(volumeName);
      URIBuilder builder = new URIBuilder(ozoneRestUri);
      builder
          .setPath(PATH_SEPARATOR + volumeName + PATH_SEPARATOR + bucketName);
      builder.addParameter(Header.OZONE_INFO_QUERY_TAG,
          Header.OZONE_INFO_QUERY_KEY);
      builder.addParameter(Header.OZONE_LIST_QUERY_MAXKEYS,
          String.valueOf(maxListResult));
      addQueryParamter(Header.OZONE_LIST_QUERY_PREFIX, keyPrefix, builder);
      addQueryParamter(Header.OZONE_LIST_QUERY_PREVKEY, prevKey, builder);
      HttpGet httpGet = new HttpGet(builder.build());
      addOzoneHeaders(httpGet);
      HttpEntity response = executeHttpRequest(httpGet);
      ListKeys keyList = ListKeys.parse(EntityUtils.toString(response));
      EntityUtils.consume(response);
      return keyList.getKeyList().stream().map(keyInfo -> {
        long creationTime = 0, modificationTime = 0;
        try {
          creationTime = HddsClientUtils.formatDateTime(keyInfo.getCreatedOn());
          modificationTime =
              HddsClientUtils.formatDateTime(keyInfo.getModifiedOn());
        } catch (ParseException e) {
          LOG.warn("Parse exception in getting creation time for volume", e);
        }
        return new OzoneKey(volumeName, bucketName, keyInfo.getKeyName(),
            keyInfo.getSize(), creationTime, modificationTime);
      }).collect(Collectors.toList());
    } catch (URISyntaxException e) {
      throw new IOException(e);
    }
  }

};