//,temp,SecondaryNameNode.java,947,998,temp,BackupImage.java,116,148
//,3
public class xxx {
  void recoverCreateRead() throws IOException {
    for (Iterator<StorageDirectory> it = storage.dirIterator(); it.hasNext();) {
      StorageDirectory sd = it.next();
      StorageState curState;
      try {
        curState = sd.analyzeStorage(HdfsServerConstants.StartupOption.REGULAR, storage);
        // sd is locked but not opened
        switch(curState) {
        case NON_EXISTENT:
          // fail if any of the configured storage dirs are inaccessible
          throw new InconsistentFSStateException(sd.getRoot(),
                "checkpoint directory does not exist or is not accessible.");
        case NOT_FORMATTED:
          // for backup node all directories may be unformatted initially
          LOG.info("Storage directory " + sd.getRoot() + " is not formatted.");
          LOG.info("Formatting ...");
          sd.clearDirectory(); // create empty current
          break;
        case NORMAL:
          break;
        default:  // recovery is possible
          sd.doRecover(curState);
        }
        if(curState != StorageState.NOT_FORMATTED) {
          // read and verify consistency with other directories
          storage.readProperties(sd);
        }
      } catch(IOException ioe) {
        sd.unlock();
        throw ioe;
      }
    }
  }

};