//,temp,SwiftNativeFileSystem.java,156,162,temp,CGroupsResourceCalculator.java,146,152
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