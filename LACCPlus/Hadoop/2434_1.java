//,temp,BucketManagerImpl.java,261,297,temp,VolumeManagerImpl.java,305,338
//,3
public class xxx {
  public void deleteBucket(String volumeName, String bucketName)
      throws IOException {
    Preconditions.checkNotNull(volumeName);
    Preconditions.checkNotNull(bucketName);
    metadataManager.writeLock().lock();
    try {
      byte[] bucketKey = metadataManager.getBucketKey(volumeName, bucketName);
      //Check if volume exists
      if (metadataManager.get(metadataManager.getVolumeKey(volumeName))
          == null) {
        LOG.debug("volume: {} not found ", volumeName);
        throw new OMException("Volume doesn't exist",
            OMException.ResultCodes.FAILED_VOLUME_NOT_FOUND);
      }
      //Check if bucket exist
      if (metadataManager.get(bucketKey) == null) {
        LOG.debug("bucket: {} not found ", bucketName);
        throw new OMException("Bucket doesn't exist",
            OMException.ResultCodes.FAILED_BUCKET_NOT_FOUND);
      }
      //Check if bucket is empty
      if (!metadataManager.isBucketEmpty(volumeName, bucketName)) {
        LOG.debug("bucket: {} is not empty ", bucketName);
        throw new OMException("Bucket is not empty",
            OMException.ResultCodes.FAILED_BUCKET_NOT_EMPTY);
      }
      metadataManager.delete(bucketKey);
    } catch (IOException ex) {
      if (!(ex instanceof OMException)) {
        LOG.error("Delete bucket failed for bucket:{} in volume:{}", bucketName,
            volumeName, ex);
      }
      throw ex;
    } finally {
      metadataManager.writeLock().unlock();
    }
  }

};