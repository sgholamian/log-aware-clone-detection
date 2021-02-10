//,temp,BlockPoolSliceStorage.java,786,803,temp,BlockPoolSliceStorage.java,764,779
//,3
public class xxx {
  public void clearRollingUpgradeMarkers(List<StorageDirectory> dnStorageDirs)
      throws IOException {
    for (StorageDirectory sd : dnStorageDirs) {
      File bpRoot = getBpRoot(blockpoolID, sd.getCurrentDir());
      File markerFile = new File(bpRoot, ROLLING_UPGRADE_MARKER_FILE);
      if (!storagesWithoutRollingUpgradeMarker.contains(bpRoot.toString())) {
        if (markerFile.exists()) {
          LOG.info("Deleting " + markerFile);
          doFinalize(sd.getCurrentDir());
          if (!markerFile.delete()) {
            LOG.warn("Failed to delete " + markerFile);
          }
        }
        storagesWithoutRollingUpgradeMarker.add(bpRoot.toString());
        storagesWithRollingUpgradeMarker.remove(bpRoot.toString());
      }
    }
  }

};