//,temp,ResourceLocalizationService.java,604,616,temp,TestSwiftFileSystemBasicOps.java,119,127
//,3
public class xxx {
  private void delete(SwiftNativeFileSystem fs, Path path) {
    try {
      if (!fs.delete(path, false)) {
        LOG.warn("Failed to delete " + path);
      }
    } catch (IOException e) {
      LOG.warn("deleting " + path, e);
    }
  }

};