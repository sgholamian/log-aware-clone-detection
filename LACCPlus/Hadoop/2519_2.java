//,temp,RestClient.java,752,788,temp,RestClient.java,351,387
//,3
public class xxx {
  @Override
  public List<OzoneVolume> listVolumes(String user, String volumePrefix,
                                       String prevKey, int maxListResult)
      throws IOException {
    try {
      URIBuilder builder = new URIBuilder(ozoneRestUri);
      builder.setPath(PATH_SEPARATOR);
      builder.addParameter(Header.OZONE_INFO_QUERY_TAG,
          Header.OZONE_LIST_QUERY_SERVICE);
      builder.addParameter(Header.OZONE_LIST_QUERY_MAXKEYS,
          String.valueOf(maxListResult));
      addQueryParamter(Header.OZONE_LIST_QUERY_PREFIX, volumePrefix, builder);
      addQueryParamter(Header.OZONE_LIST_QUERY_PREVKEY, prevKey, builder);
      HttpGet httpGet = new HttpGet(builder.build());
      if (!Strings.isNullOrEmpty(user)) {
        httpGet.addHeader(Header.OZONE_USER, user);
      }
      addOzoneHeaders(httpGet);
      HttpEntity response = executeHttpRequest(httpGet);
      ListVolumes volumeList =
          ListVolumes.parse(EntityUtils.toString(response));
      EntityUtils.consume(response);
      return volumeList.getVolumes().stream().map(volInfo -> {
        long creationTime = 0;
        try {
          creationTime = HddsClientUtils.formatDateTime(volInfo.getCreatedOn());
        } catch (ParseException e) {
          LOG.warn("Parse exception in getting creation time for volume", e);
        }
        return new OzoneVolume(conf, this, volInfo.getVolumeName(),
            volInfo.getCreatedBy(), volInfo.getOwner().getName(),
            volInfo.getQuota().sizeInBytes(), creationTime, null);
      }).collect(Collectors.toList());
    } catch (URISyntaxException e) {
      throw new IOException(e);
    }
  }

};