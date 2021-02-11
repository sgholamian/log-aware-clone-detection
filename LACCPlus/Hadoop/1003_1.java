//,temp,FSNamesystem.java,4826,4842,temp,FSNamesystem.java,2507,2525
//,3
public class xxx {
  NamenodeCommand startCheckpoint(NamenodeRegistration backupNode,
      NamenodeRegistration activeNamenode) throws IOException {
    checkOperation(OperationCategory.CHECKPOINT);
    writeLock();
    try {
      checkOperation(OperationCategory.CHECKPOINT);
      checkNameNodeSafeMode("Checkpoint not started");
      
      LOG.info("Start checkpoint for " + backupNode.getAddress());
      NamenodeCommand cmd = getFSImage().startCheckpoint(backupNode,
          activeNamenode, getEffectiveLayoutVersion());
      getEditLog().logSync();
      return cmd;
    } finally {
      writeUnlock();
    }
  }

};