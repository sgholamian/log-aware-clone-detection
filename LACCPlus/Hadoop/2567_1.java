//,temp,SwiftNativeFileSystem.java,442,448,temp,SwiftNativeFileSystem.java,156,162
//,3
public class xxx {
  @Override
  public FileStatus[] listStatus(Path path) throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("SwiftFileSystem.listStatus for: " + path);
    }
    return store.listSubPaths(makeAbsolute(path), false, true);
  }

};