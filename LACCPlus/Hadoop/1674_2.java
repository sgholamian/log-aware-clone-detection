//,temp,FSDirWriteFileOp.java,778,787,temp,FSDirWriteFileOp.java,97,107
//,3
public class xxx {
  static void persistBlocks(
      FSDirectory fsd, String path, INodeFile file, boolean logRetryCache) {
    assert fsd.getFSNamesystem().hasWriteLock();
    Preconditions.checkArgument(file.isUnderConstruction());
    fsd.getEditLog().logUpdateBlocks(path, file, logRetryCache);
    if(NameNode.stateChangeLog.isDebugEnabled()) {
      NameNode.stateChangeLog.debug("persistBlocks: " + path
              + " with " + file.getBlocks().length + " blocks is persisted to" +
              " the file system");
    }
  }

};