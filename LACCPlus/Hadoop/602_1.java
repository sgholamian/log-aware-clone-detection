//,temp,HistoryFileManager.java,703,721,temp,HistoryFileManager.java,686,701
//,3
public class xxx {
  private void addDirectoryToSerialNumberIndex(Path serialDirPath) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Adding " + serialDirPath + " to serial index");
    }
    String serialPart = serialDirPath.getName();
    String timestampPart = JobHistoryUtils
        .getTimestampPartFromPath(serialDirPath.toString());
    if (timestampPart == null) {
      LOG.warn("Could not find timestamp portion from path: " + serialDirPath
          + ". Continuing with next");
      return;
    }
    if (serialPart == null) {
      LOG.warn("Could not find serial portion from path: "
          + serialDirPath.toString() + ". Continuing with next");
    } else {
      serialNumberIndex.add(serialPart, timestampPart);
    }
  }

};