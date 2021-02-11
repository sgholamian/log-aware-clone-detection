//,temp,FsVolumeList.java,313,347,temp,FsVolumeList.java,278,307
//,3
public class xxx {
  private void removeVolume(FsVolumeImpl target) {
    while (true) {
      final FsVolumeImpl[] curVolumes = volumes.get();
      final List<FsVolumeImpl> volumeList = Lists.newArrayList(curVolumes);
      if (volumeList.remove(target)) {
        if (volumes.compareAndSet(curVolumes,
            volumeList.toArray(new FsVolumeImpl[volumeList.size()]))) {
          if (blockScanner != null) {
            blockScanner.removeVolumeScanner(target);
          }
          try {
            target.closeAndWait();
          } catch (IOException e) {
            FsDatasetImpl.LOG.warn(
                "Error occurs when waiting volume to close: " + target, e);
          }
          target.shutdown();
          FsDatasetImpl.LOG.info("Removed volume: " + target);
          break;
        } else {
          if (FsDatasetImpl.LOG.isDebugEnabled()) {
            FsDatasetImpl.LOG.debug(
                "The volume list has been changed concurrently, " +
                "retry to remove volume: " + target);
          }
        }
      } else {
        if (FsDatasetImpl.LOG.isDebugEnabled()) {
          FsDatasetImpl.LOG.debug("Volume " + target +
              " does not exist or is removed by others.");
        }
        break;
      }
    }
  }

};