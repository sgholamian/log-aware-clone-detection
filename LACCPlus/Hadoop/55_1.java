//,temp,TestSwiftFileSystemBasicOps.java,118,126,temp,DistCpSync.java,121,130
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