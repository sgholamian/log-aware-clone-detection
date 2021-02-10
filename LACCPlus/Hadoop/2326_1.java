//,temp,StateStoreFileImpl.java,100,114,temp,StateStoreFileSystemImpl.java,149,162
//,3
public class xxx {
  @Override
  protected <T extends BaseRecord> BufferedReader getReader(String filename) {
    BufferedReader reader = null;
    try {
      LOG.debug("Loading file: {}", filename);
      File file = new File(filename);
      FileInputStream fis = new FileInputStream(file);
      InputStreamReader isr =
          new InputStreamReader(fis, StandardCharsets.UTF_8);
      reader = new BufferedReader(isr);
    } catch (Exception ex) {
      LOG.error("Cannot open read stream for record {}", filename, ex);
    }
    return reader;
  }

};