//,temp,TestSwiftFileSystemBasicOps.java,128,136,temp,TestSwiftFileSystemBasicOps.java,118,126
//,2
public class xxx {
  private void deleteR(SwiftNativeFileSystem fs, Path path) {
    try {
      if (!fs.delete(path, true)) {
        LOG.warn("Failed to delete " + path);
      }
    } catch (IOException e) {
      LOG.warn("deleting " + path, e);
    }
  }

};