//,temp,BucketManagerImpl.java,129,154,temp,VolumeManagerImpl.java,274,297
//,3
public class xxx {
  public OmVolumeArgs getVolumeInfo(String volume) throws IOException {
    Preconditions.checkNotNull(volume);
    metadataManager.readLock().lock();
    try {
      byte[] dbVolumeKey = metadataManager.getVolumeKey(volume);
      byte[] volInfo = metadataManager.get(dbVolumeKey);
      if (volInfo == null) {
        LOG.debug("volume:{} does not exist", volume);
        throw new OMException(ResultCodes.FAILED_VOLUME_NOT_FOUND);
      }

      VolumeInfo volumeInfo = VolumeInfo.parseFrom(volInfo);
      OmVolumeArgs volumeArgs = OmVolumeArgs.getFromProtobuf(volumeInfo);
      Preconditions.checkState(volume.equals(volumeInfo.getVolume()));
      return volumeArgs;
    } catch (IOException ex) {
      if (!(ex instanceof OMException)) {
        LOG.warn("Info volume failed for volume:{}", volume, ex);
      }
      throw ex;
    } finally {
      metadataManager.readLock().unlock();
    }
  }

};