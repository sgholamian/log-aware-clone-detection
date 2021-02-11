//,temp,HistoryFileManager.java,703,721,temp,HistoryFileManager.java,686,701
//,3
public class xxx {
  private void removeDirectoryFromSerialNumberIndex(Path serialDirPath) {
    String serialPart = serialDirPath.getName();
    String timeStampPart = JobHistoryUtils
        .getTimestampPartFromPath(serialDirPath.toString());
    if (timeStampPart == null) {
      LOG.warn("Could not find timestamp portion from path: "
          + serialDirPath.toString() + ". Continuing with next");
      return;
    }
    if (serialPart == null) {
      LOG.warn("Could not find serial portion from path: "
          + serialDirPath.toString() + ". Continuing with next");
      return;
    }
    serialNumberIndex.remove(serialPart, timeStampPart);
  }

};