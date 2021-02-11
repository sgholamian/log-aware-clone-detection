//,temp,StateStoreFileBaseImpl.java,173,192,temp,StateStoreFileBaseImpl.java,148,171
//,3
public class xxx {
  @Override
  public <T extends BaseRecord> boolean initRecordStorage(
      String className, Class<T> recordClass) {

    String dataDirPath = getRootDir() + "/" + className;
    try {
      // Create data directories for files
      if (!exists(dataDirPath)) {
        LOG.info("{} data directory doesn't exist, creating it", dataDirPath);
        if (!mkdir(dataDirPath)) {
          LOG.error("Cannot create data directory {}", dataDirPath);
          return false;
        }
      }
    } catch (Exception ex) {
      LOG.error("Cannot create data directory {}", dataDirPath, ex);
      return false;
    }
    return true;
  }

};