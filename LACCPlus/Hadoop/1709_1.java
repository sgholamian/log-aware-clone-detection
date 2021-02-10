//,temp,TestSwiftFileSystemBasicOps.java,118,126,temp,TestDistCpUtils.java,1038,1048
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