//,temp,BlockPoolSliceStorage.java,786,803,temp,BlockPoolSliceStorage.java,764,779
//,3
public class xxx {
  public void setRollingUpgradeMarkers(List<StorageDirectory> dnStorageDirs)
      throws IOException {
    for (StorageDirectory sd : dnStorageDirs) {
      File bpRoot = getBpRoot(blockpoolID, sd.getCurrentDir());
      File markerFile = new File(bpRoot, ROLLING_UPGRADE_MARKER_FILE);
      if (!storagesWithRollingUpgradeMarker.contains(bpRoot.toString())) {
        if (!markerFile.exists() && markerFile.createNewFile()) {
          LOG.info("Created " + markerFile);
        } else {
          LOG.info(markerFile + " already exists.");
        }
        storagesWithRollingUpgradeMarker.add(bpRoot.toString());
        storagesWithoutRollingUpgradeMarker.remove(bpRoot.toString());
      }
    }
  }

};