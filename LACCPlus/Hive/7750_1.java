//,temp,KeyValueContainer.java,74,97,temp,HiveKVResultCache.java,88,117
//,3
public class xxx {
  private void setupOutput(String spillLocalDirs) throws IOException, HiveException {
    FileOutputStream fos = null;
    try {
      if (parentDir == null) {
        parentDir = FileUtils.createLocalDirsTempFile(spillLocalDirs, "key-value-container", "", true);
        parentDir.deleteOnExit();
      }

      if (tmpFile == null || input != null) {
        tmpFile = File.createTempFile("KeyValueContainer", ".tmp", parentDir);
        LOG.info("KeyValueContainer created temp file " + tmpFile.getAbsolutePath());
        tmpFile.deleteOnExit();
      }

      fos = new FileOutputStream(tmpFile);
      output = new Output(fos);
    } catch (IOException e) {
      throw new HiveException(e);
    } finally {
      if (output == null && fos != null) {
        fos.close();
      }
    }
  }

};