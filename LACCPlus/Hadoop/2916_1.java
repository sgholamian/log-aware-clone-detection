//,temp,BlockPoolSliceStorage.java,876,896,temp,BlockPoolSliceStorage.java,851,869
//,3
public class xxx {
  public void clearRollingUpgradeMarkers(List<StorageDirectory> dnStorageDirs)
      throws IOException {
    for (StorageDirectory sd : dnStorageDirs) {
      if (sd.getCurrentDir() == null) {
        continue;
      }
      File bpRoot = getBpRoot(blockpoolID, sd.getCurrentDir());
      File markerFile = new File(bpRoot, ROLLING_UPGRADE_MARKER_FILE);
      if (!storagesWithoutRollingUpgradeMarker.contains(bpRoot.toString())) {
        if (markerFile.exists()) {
          LOG.info("Deleting {}", markerFile);
          doFinalize(sd.getCurrentDir());
          if (!markerFile.delete()) {
            LOG.warn("Failed to delete {}", markerFile);
          }
        }
        storagesWithoutRollingUpgradeMarker.add(bpRoot.toString());
        storagesWithRollingUpgradeMarker.remove(bpRoot.toString());
      }
    }
  }

};