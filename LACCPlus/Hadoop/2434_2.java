//,temp,BucketManagerImpl.java,261,297,temp,VolumeManagerImpl.java,305,338
//,3
public class xxx {
  @Override
  public void deleteVolume(String volume) throws IOException {
    Preconditions.checkNotNull(volume);
    metadataManager.writeLock().lock();
    try {
      BatchOperation batch = new BatchOperation();
      byte[] dbVolumeKey = metadataManager.getVolumeKey(volume);
      byte[] volInfo = metadataManager.get(dbVolumeKey);
      if (volInfo == null) {
        LOG.debug("volume:{} does not exist", volume);
        throw new OMException(ResultCodes.FAILED_VOLUME_NOT_FOUND);
      }

      if (!metadataManager.isVolumeEmpty(volume)) {
        LOG.debug("volume:{} is not empty", volume);
        throw new OMException(ResultCodes.FAILED_VOLUME_NOT_EMPTY);
      }

      VolumeInfo volumeInfo = VolumeInfo.parseFrom(volInfo);
      Preconditions.checkState(volume.equals(volumeInfo.getVolume()));
      // delete the volume from the owner list
      // as well as delete the volume entry
      delVolumeFromOwnerList(volume, volumeInfo.getOwnerName(), batch);
      batch.delete(dbVolumeKey);
      metadataManager.writeBatch(batch);
    } catch (IOException ex) {
      if (!(ex instanceof OMException)) {
        LOG.error("Delete volume failed for volume:{}", volume, ex);
      }
      throw ex;
    } finally {
      metadataManager.writeLock().unlock();
    }
  }

};