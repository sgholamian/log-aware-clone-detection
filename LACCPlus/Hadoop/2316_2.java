//,temp,VolumeSet.java,255,274,temp,VolumeSet.java,226,252
//,3
public class xxx {
  public boolean addVolume(String volumeRoot, StorageType storageType) {
    String hddsRoot = HddsVolumeUtil.getHddsRoot(volumeRoot);
    boolean success;

    try (AutoCloseableLock lock = volumeSetLock.acquire()) {
      if (volumeMap.containsKey(hddsRoot)) {
        LOG.warn("Volume : {} already exists in VolumeMap", hddsRoot);
        success = false;
      } else {
        if (failedVolumeMap.containsKey(hddsRoot)) {
          failedVolumeMap.remove(hddsRoot);
        }

        HddsVolume hddsVolume = createVolume(volumeRoot, storageType);
        volumeMap.put(hddsVolume.getHddsRootDir().getPath(), hddsVolume);
        volumeStateMap.get(hddsVolume.getStorageType()).add(hddsVolume);

        LOG.info("Added Volume : {} to VolumeSet",
            hddsVolume.getHddsRootDir().getPath());
        success = true;
      }
    } catch (IOException ex) {
      LOG.error("Failed to add volume " + volumeRoot + " to VolumeSet", ex);
      success = false;
    }
    return success;
  }

};