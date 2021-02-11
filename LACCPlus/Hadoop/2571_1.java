//,temp,SwiftNativeFileSystem.java,156,162,temp,DeleteCompletionCallback.java,40,48
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