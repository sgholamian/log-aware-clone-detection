//,temp,NMTokenSecretManagerInNM.java,249,254,temp,SwiftNativeFileSystem.java,156,162
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