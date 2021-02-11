//,temp,VolumeSet.java,255,274,temp,VolumeSet.java,226,252
//,3
public class xxx {
  public void failVolume(String dataDir) {
    String hddsRoot = HddsVolumeUtil.getHddsRoot(dataDir);

    try (AutoCloseableLock lock = volumeSetLock.acquire()) {
      if (volumeMap.containsKey(hddsRoot)) {
        HddsVolume hddsVolume = volumeMap.get(hddsRoot);
        hddsVolume.failVolume();

        volumeMap.remove(hddsRoot);
        volumeStateMap.get(hddsVolume.getStorageType()).remove(hddsVolume);
        failedVolumeMap.put(hddsRoot, hddsVolume);

        LOG.info("Moving Volume : {} to failed Volumes", hddsRoot);
      } else if (failedVolumeMap.containsKey(hddsRoot)) {
        LOG.info("Volume : {} is not active", hddsRoot);
      } else {
        LOG.warn("Volume : {} does not exist in VolumeSet", hddsRoot);
      }
    }
  }

};