//,temp,Jets3tNativeFileSystemStore.java,224,237,temp,Jets3tNativeFileSystemStore.java,202,213
//,3
public class xxx {
  @Override
  public InputStream retrieve(String key, long byteRangeStart)
          throws IOException {
    try {
      LOG.debug("Getting key: {} from bucket: {} with byteRangeStart: {}",
          key, bucket.getName(), byteRangeStart);
      S3Object object = s3Service.getObject(bucket, key, null, null, null,
                                            null, byteRangeStart, null);
      return object.getDataInputStream();
    } catch (ServiceException e) {
      handleException(e, key);
      return null;
    }
  }

};