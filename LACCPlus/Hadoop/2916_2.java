//,temp,BlockPoolSliceStorage.java,876,896,temp,BlockPoolSliceStorage.java,851,869
//,3
public class xxx {
  public void setRollingUpgradeMarkers(List<StorageDirectory> dnStorageDirs)
      throws IOException {
    for (StorageDirectory sd : dnStorageDirs) {
      if (sd.getCurrentDir() == null) {
        return;
      }
      File bpRoot = getBpRoot(blockpoolID, sd.getCurrentDir());
      File markerFile = new File(bpRoot, ROLLING_UPGRADE_MARKER_FILE);
      if (!storagesWithRollingUpgradeMarker.contains(bpRoot.toString())) {
        if (!markerFile.exists() && markerFile.createNewFile()) {
          LOG.info("Created {}", markerFile);
        } else {
          LOG.info("{} already exists.", markerFile);
        }
        storagesWithRollingUpgradeMarker.add(bpRoot.toString());
        storagesWithoutRollingUpgradeMarker.remove(bpRoot.toString());
      }
    }
  }

};