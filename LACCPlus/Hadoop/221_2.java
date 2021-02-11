//,temp,Jets3tNativeFileSystemStore.java,224,237,temp,Jets3tNativeFileSystemStore.java,202,213
//,3
public class xxx {
  @Override
  public InputStream retrieve(String key) throws IOException {
    try {
      LOG.debug("Getting key: {} from bucket: {}",
          key, bucket.getName());
      S3Object object = s3Service.getObject(bucket.getName(), key);
      return object.getDataInputStream();
    } catch (ServiceException e) {
      handleException(e, key);
      return null; //return null if key not found
    }
  }

};