//,temp,FSDirWriteFileOp.java,778,787,temp,FSDirWriteFileOp.java,97,107
//,3
public class xxx {
  private static void persistNewBlock(
      FSNamesystem fsn, String path, INodeFile file) {
    Preconditions.checkArgument(file.isUnderConstruction());
    fsn.getEditLog().logAddBlock(path, file);
    if (NameNode.stateChangeLog.isDebugEnabled()) {
      NameNode.stateChangeLog.debug("persistNewBlock: "
              + path + " with new block " + file.getLastBlock().toString()
              + ", current total block count is " + file.getBlocks().length);
    }
  }

};