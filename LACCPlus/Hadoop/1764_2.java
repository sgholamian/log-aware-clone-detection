//,temp,FsVolumeList.java,313,347,temp,FsVolumeList.java,278,307
//,3
public class xxx {
  void addVolume(FsVolumeReference ref) {
    FsVolumeImpl volume = (FsVolumeImpl) ref.getVolume();
    while (true) {
      final FsVolumeImpl[] curVolumes = volumes.get();
      final List<FsVolumeImpl> volumeList = Lists.newArrayList(curVolumes);
      volumeList.add(volume);
      if (volumes.compareAndSet(curVolumes,
          volumeList.toArray(new FsVolumeImpl[volumeList.size()]))) {
        break;
      } else {
        if (FsDatasetImpl.LOG.isDebugEnabled()) {
          FsDatasetImpl.LOG.debug(
              "The volume list has been changed concurrently, " +
                  "retry to remove volume: " + ref.getVolume().getStorageID());
        }
      }
    }
    if (blockScanner != null) {
      blockScanner.addVolumeScanner(ref);
    } else {
      // If the volume is not put into a volume scanner, it does not need to
      // hold the reference.
      IOUtils.cleanup(FsDatasetImpl.LOG, ref);
    }
    // If the volume is used to replace a failed volume, it needs to reset the
    // volume failure info for this volume.
    removeVolumeFailureInfo(new File(volume.getBasePath()));
    FsDatasetImpl.LOG.info("Added new volume: " +
        volume.getStorageID());
  }

};