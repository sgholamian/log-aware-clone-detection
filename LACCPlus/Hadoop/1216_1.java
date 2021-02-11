//,temp,TestSwiftFileSystemBasicOps.java,128,136,temp,TestDistCpUtils.java,1038,1048
//,3
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