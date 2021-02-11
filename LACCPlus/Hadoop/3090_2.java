//,temp,StateStoreFileImpl.java,116,130,temp,StateStoreFileSystemImpl.java,149,162
//,3
public class xxx {
  @Override
  protected <T extends BaseRecord> BufferedWriter getWriter(String pathName) {
    BufferedWriter writer = null;
    Path path = new Path(pathName);
    try {
      FSDataOutputStream fdos = fs.create(path, true);
      OutputStreamWriter osw =
          new OutputStreamWriter(fdos, StandardCharsets.UTF_8);
      writer = new BufferedWriter(osw);
    } catch (IOException ex) {
      LOG.error("Cannot open write stream for {}", path);
    }
    return writer;
  }

};