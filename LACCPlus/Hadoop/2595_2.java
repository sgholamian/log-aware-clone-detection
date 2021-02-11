//,temp,ApplicationMaster.java,1211,1217,temp,SwiftNativeFileSystem.java,426,432
//,3
public class xxx {
  private void forceMkdir(Path absolutePath) throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Making dir '" + absolutePath + "' in Swift");
    }
    //file is not found: it must be created
    store.createDirectory(absolutePath);
  }

};