//,temp,BucketManagerImpl.java,161,232,temp,BucketManagerImpl.java,78,121
//,3
public class xxx {
  @Override
  public void createBucket(OmBucketInfo bucketInfo) throws IOException {
    Preconditions.checkNotNull(bucketInfo);
    metadataManager.writeLock().lock();
    String volumeName = bucketInfo.getVolumeName();
    String bucketName = bucketInfo.getBucketName();
    try {
      byte[] volumeKey = metadataManager.getVolumeKey(volumeName);
      byte[] bucketKey = metadataManager.getBucketKey(volumeName, bucketName);

      //Check if the volume exists
      if (metadataManager.get(volumeKey) == null) {
        LOG.debug("volume: {} not found ", volumeName);
        throw new OMException("Volume doesn't exist",
            OMException.ResultCodes.FAILED_VOLUME_NOT_FOUND);
      }
      //Check if bucket already exists
      if (metadataManager.get(bucketKey) != null) {
        LOG.debug("bucket: {} already exists ", bucketName);
        throw new OMException("Bucket already exist",
            OMException.ResultCodes.FAILED_BUCKET_ALREADY_EXISTS);
      }

      OmBucketInfo omBucketInfo = OmBucketInfo.newBuilder()
          .setVolumeName(bucketInfo.getVolumeName())
          .setBucketName(bucketInfo.getBucketName())
          .setAcls(bucketInfo.getAcls())
          .setStorageType(bucketInfo.getStorageType())
          .setIsVersionEnabled(bucketInfo.getIsVersionEnabled())
          .setCreationTime(Time.now())
          .build();
      metadataManager.put(bucketKey, omBucketInfo.getProtobuf().toByteArray());

      LOG.debug("created bucket: {} in volume: {}", bucketName, volumeName);
    } catch (IOException | DBException ex) {
      if (!(ex instanceof OMException)) {
        LOG.error("Bucket creation failed for bucket:{} in volume:{}",
            bucketName, volumeName, ex);
      }
      throw ex;
    } finally {
      metadataManager.writeLock().unlock();
    }
  }

};