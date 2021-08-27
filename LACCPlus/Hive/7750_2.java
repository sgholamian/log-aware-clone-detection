//,temp,KeyValueContainer.java,74,97,temp,HiveKVResultCache.java,88,117
//,3
public class xxx {
  private void setupOutput() throws IOException {
    if (parentFile == null) {
      while (true) {
        parentFile = File.createTempFile("hive-resultcache", "");
        if (parentFile.delete() && parentFile.mkdir()) {
          parentFile.deleteOnExit();
          break;
        }
        if (LOG.isDebugEnabled()) {
          LOG.debug("Retry creating tmp result-cache directory...");
        }
      }
    }

    if (tmpFile == null || input != null) {
      tmpFile = File.createTempFile("ResultCache", ".tmp", parentFile);
      LOG.info("ResultCache created temp file " + tmpFile.getAbsolutePath());
      tmpFile.deleteOnExit();
    }

    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(tmpFile);
      output = new Output(fos);
    } finally {
      if (output == null && fos != null) {
        fos.close();
      }
    }
  }

};