//,temp,FSNamesystem.java,7539,7560,temp,FSNamesystem.java,4671,4687
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
      writeUnlock("startCheckpoint");
    }
  }

};