//,temp,FileSystemApplicationHistoryStore.java,195,217,temp,StateStoreFileSystemImpl.java,164,179
//,3
public class xxx {
  @Override
  protected List<String> getChildren(String pathName) {
    List<String> ret = new LinkedList<>();
    Path path = new Path(workPath, pathName);
    try {
      FileStatus[] files = fs.listStatus(path);
      for (FileStatus file : files) {
        Path filePath = file.getPath();
        String fileName = filePath.getName();
        ret.add(fileName);
      }
    } catch (Exception e) {
      LOG.error("Cannot get children for {}", pathName, e);
    }
    return ret;
  }

};