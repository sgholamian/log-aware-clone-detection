//,temp,BucketManagerImpl.java,129,154,temp,VolumeManagerImpl.java,274,297
//,3
public class xxx {
  @Override
  public OmBucketInfo getBucketInfo(String volumeName, String bucketName)
      throws IOException {
    Preconditions.checkNotNull(volumeName);
    Preconditions.checkNotNull(bucketName);
    metadataManager.readLock().lock();
    try {
      byte[] bucketKey = metadataManager.getBucketKey(volumeName, bucketName);
      byte[] value = metadataManager.get(bucketKey);
      if (value == null) {
        LOG.debug("bucket: {} not found in volume: {}.", bucketName,
            volumeName);
        throw new OMException("Bucket not found",
            OMException.ResultCodes.FAILED_BUCKET_NOT_FOUND);
      }
      return OmBucketInfo.getFromProtobuf(BucketInfo.parseFrom(value));
    } catch (IOException | DBException ex) {
      if (!(ex instanceof OMException)) {
        LOG.error("Exception while getting bucket info for bucket: {}",
            bucketName, ex);
      }
      throw ex;
    } finally {
      metadataManager.readLock().unlock();
    }
  }

};