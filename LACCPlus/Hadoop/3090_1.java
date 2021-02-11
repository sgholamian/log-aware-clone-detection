//,temp,StateStoreFileImpl.java,116,130,temp,StateStoreFileSystemImpl.java,149,162
//,3
public class xxx {
  @Override
  protected <T extends BaseRecord> BufferedWriter getWriter(String filename) {
    BufferedWriter writer = null;
    try {
      LOG.debug("Writing file: {}", filename);
      File file = new File(filename);
      FileOutputStream fos = new FileOutputStream(file, false);
      OutputStreamWriter osw =
          new OutputStreamWriter(fos, StandardCharsets.UTF_8);
      writer = new BufferedWriter(osw);
    } catch (IOException e) {
      LOG.error("Cannot open write stream for record {}", filename, e);
    }
    return writer;
  }

};