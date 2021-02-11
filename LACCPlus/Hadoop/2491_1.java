//,temp,BucketManagerImpl.java,161,232,temp,BucketManagerImpl.java,78,121
//,3
public class xxx {
  @Override
  public void setBucketProperty(OmBucketArgs args) throws IOException {
    Preconditions.checkNotNull(args);
    metadataManager.writeLock().lock();
    String volumeName = args.getVolumeName();
    String bucketName = args.getBucketName();
    try {
      byte[] bucketKey = metadataManager.getBucketKey(volumeName, bucketName);
      //Check if volume exists
      if(metadataManager.get(metadataManager.getVolumeKey(volumeName)) ==
          null) {
        LOG.debug("volume: {} not found ", volumeName);
        throw new OMException("Volume doesn't exist",
            OMException.ResultCodes.FAILED_VOLUME_NOT_FOUND);
      }
      byte[] value = metadataManager.get(bucketKey);
      //Check if bucket exist
      if(value == null) {
        LOG.debug("bucket: {} not found ", bucketName);
        throw new OMException("Bucket doesn't exist",
            OMException.ResultCodes.FAILED_BUCKET_NOT_FOUND);
      }
      OmBucketInfo oldBucketInfo = OmBucketInfo.getFromProtobuf(
          BucketInfo.parseFrom(value));
      OmBucketInfo.Builder bucketInfoBuilder = OmBucketInfo.newBuilder();
      bucketInfoBuilder.setVolumeName(oldBucketInfo.getVolumeName())
          .setBucketName(oldBucketInfo.getBucketName());

      //Check ACLs to update
      if(args.getAddAcls() != null || args.getRemoveAcls() != null) {
        bucketInfoBuilder.setAcls(getUpdatedAclList(oldBucketInfo.getAcls(),
            args.getRemoveAcls(), args.getAddAcls()));
        LOG.debug("Updating ACLs for bucket: {} in volume: {}",
            bucketName, volumeName);
      } else {
        bucketInfoBuilder.setAcls(oldBucketInfo.getAcls());
      }

      //Check StorageType to update
      StorageType storageType = args.getStorageType();
      if (storageType != null) {
        bucketInfoBuilder.setStorageType(storageType);
        LOG.debug("Updating bucket storage type for bucket: {} in volume: {}",
            bucketName, volumeName);
      } else {
        bucketInfoBuilder.setStorageType(oldBucketInfo.getStorageType());
      }

      //Check Versioning to update
      Boolean versioning = args.getIsVersionEnabled();
      if (versioning != null) {
        bucketInfoBuilder.setIsVersionEnabled(versioning);
        LOG.debug("Updating bucket versioning for bucket: {} in volume: {}",
            bucketName, volumeName);
      } else {
        bucketInfoBuilder
            .setIsVersionEnabled(oldBucketInfo.getIsVersionEnabled());
      }
      bucketInfoBuilder.setCreationTime(oldBucketInfo.getCreationTime());

      metadataManager.put(bucketKey,
          bucketInfoBuilder.build().getProtobuf().toByteArray());
    } catch (IOException | DBException ex) {
      if (!(ex instanceof OMException)) {
        LOG.error("Setting bucket property failed for bucket:{} in volume:{}",
            bucketName, volumeName, ex);
      }
      throw ex;
    } finally {
      metadataManager.writeLock().unlock();
    }
  }

};