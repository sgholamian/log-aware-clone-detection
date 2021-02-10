//,temp,AbstractJavaKeyStoreProvider.java,161,167,temp,SwiftNativeFileSystem.java,156,162
//,3
public class xxx {
  @Override
  public void setWorkingDirectory(Path dir) {
    workingDir = makeAbsolute(dir);
    if (LOG.isDebugEnabled()) {
      LOG.debug("SwiftFileSystem.setWorkingDirectory to " + dir);
    }
  }

};